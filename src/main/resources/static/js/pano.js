/**
 * 新增热点
 * @param hotspot：hotspot 的具体内容
 * @param callback: 回调函数
 */
function addHotspot(hotspot, callback){
    doPost("/pano/addHotspot", hotspot, function(data){
        if(callback){
            callback.call(this, data);
        }
    })
}
/**
 * 新增问题
 * @param question：question 的具体内容
 * @param callback: 回调函数
 */
function addQuestion(question, callback){
    doPost("/pano/addQuestion", question, function(data){
        if(callback){
            callback.call(this, data);
        }
    })
}
/**
 * 热点评论列表
 * @param sceneCode： 场景编号
 * @param page： 页码
 * @param size： 每页记录数
 * @param callback： 回调函数
 */
function getHotspotList(sceneCode, callback){
    getHotspotByPage(sceneCode, 1, 10000, callback);
}

function getAllHotspots(callback){
    doGet("/pano/hotspots", {}, function(data){
        if(callback){
            callback.call(this, data);
        }
    })
}
function getAllInnerHotspots(callback){
    doGet("/pano/innerHotspots", {}, function(data){
        if(callback){
            callback.call(this, data);
        }
    })
}

function getAllQuestions(callback){
    doGet("/pano/questions", {}, function(data){
        if(callback){
            callback.call(this, data);
        }
    })
}

function getAllScenes(callback){
    doGet("/pano/scenes", {}, function(data){
        if(callback){
            callback.call(this, data);
        }
    })
}

function getHotspotByPage(sceneCode, page, size, callback){
    console.log(sceneCode)
    doGet("/pano/hotspotList", {"sceneCode": sceneCode, "page": page, "size": size}, function(data){
        if(callback){
            callback.call(this, data);
        }
    })
}


/**
 * 添加点赞
 * @param comment： 点赞信息
 * @param callback: 回调函数
 */
function addThumbup(thumbup, callback){
    doPost("/thumbup/add",thumbup, function(data){
        if(callback){
            callback.call(this, data);
        }
    })
}

/**
 * 取消点赞
 * @param comment： 点赞信息
 * @param userId： 点赞人ID
 * @param callback: 回调函数
 */
function cancelThumbup(sceneCode, userId, callback){
    doPost("/thumbup/cancel",{"sceneCode": sceneCode, "userId": userId}, function(data){
        if(callback){
            callback.call(this, data);
        }
    })
}
/**
 * 获取点赞个数
 * @param thumbup： 点赞信息
 * @param callback： 回调函数
 */
function getThumbupCount(thumbup, callback){
    doGet("/thumbup/count", thumbup, function(data){
        if(callback){
            callback.call(this, data);
        }
    })
}

var syncScreen = {
    stompClient: null,
    frequent: 50,
    code: null,
    init: function(data){
        syncScreen.frequent = data.frequent;
    },
    connect: function (data, callback) {
        if(!data){
            data = {}
        }
        var socket = new SockJS('/panopipe');
        syncScreen.stompClient = Stomp.over(socket);
        syncScreen.stompClient.connect(data, function (frame) {
            console.log('Connected:' + frame);
            if(callback){
                callback.call(this, syncScreen.stompClient);
            }
        });
    },
    disconnect: function () {
        if (syncScreen.stompClient != null) {
            syncScreen.stompClient.disconnect();
        }
        console.log('Disconnected');
    },
    changePanoScene: function (krpano, krObj){
        var hlookat = krObj.hlookat;
        var vlookat = krObj.vlookat;
        var fov = krObj.fov;
        var scenepath = krObj.scenepath;
        var scene_name = scenepath.split("/")[4]
        if (krpano && krpano.set) {
            if(scenepath != krpano.get("xml.url")){
                krpano.call("loadpanoscene('" + scenepath + "', '" + scene_name + "');")
            }
            krpano.set("view.hlookat", hlookat);
            krpano.set("view.vlookat", vlookat);
            krpano.set("view.fov", fov);
        } else {
            console.log("fail to change pano scene")
        }
    },
    syncMsg: function(panoId){
        var krpano = document.getElementById(panoId);
        if (krpano && krpano.get) {
            var hlookat = krpano.get("view.hlookat");
            var vlookat = krpano.get("view.vlookat");
            var fov = krpano.get("view.fov");
            var scenepath = krpano.get("xml.url");
            var krObj = {
                hlookat: hlookat,
                vlookat: vlookat,
                fov: fov,
                scenepath: scenepath
            }
            syncScreen.stompClient.send("/ws/sendLocation", {"code": syncScreen.code}, JSON.stringify({message: krObj}));
        } else {
            console.log("pano is not undefined")
        }
    },
    syncCommonMsg: function(data){
        syncScreen.stompClient.send("/ws/sendLocation", {"code": syncScreen.code}, JSON.stringify({message: {"data":data}}));
    },
    initServer: function(panoId, callback){
        doGet("/ws/getCode", {}, function(data){
            var code = data['msg'];
            syncScreen.code = code
            syncScreen.connect({"code": code}, function(){
                if(callback){
                    callback.call(this, code);
                }
            })
        })
    },
    initClient: function(code, panoId){
        var config = {"code": code};
        syncScreen.connect(config, function(stompClient){
            var krpano = document.getElementById(panoId);
            stompClient.subscribe('/user/client/getLocation', function (frame) {
                krObj = JSON.parse(frame.body).message;
                syncScreen.changePanoScene(krpano, krObj);
            })
        })
    }

}
