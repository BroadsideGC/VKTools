package vktool


import khttp.get
import org.json.JSONArray
import org.json.JSONObject
import vktool.model.WallPost
import java.util.*

/**
 * Created by bigz on 04.10.16.
 */

class APIHelper(val token: String) {

    fun request(method: String, params: Map<String, String> = mapOf()): JSONObject {
        val reqParams = HashMap(params)
        reqParams["v"] = "5.37"
        reqParams["access_token"] = token
        //println(reqParams["access_token"])
        var res = get("https://api.vk.com/method/" + method, params = reqParams).jsonObject
        println(res)
        while (res.has("error") && (res["error"] as JSONObject)["error_code"] == "6") {
            res = get("https://api.vk.com/method/" + method, params = reqParams).jsonObject
        }
        return res["response"] as JSONObject
    }

    fun getId(name: String): String {
        val res = request("utils.resolveScreenName", mapOf("screen_name" to name))["object_id"]
        return res.toString()
    }

    fun getAllWall(id: String): List<WallPost> {
        val count = request("wall.get", mapOf("owner_id" to id, "offset" to "0", "count" to "0"))["count"] as Int
        val wall: MutableList<Any> = mutableListOf()
        for (offset in 0..count step 100) {
            wall.addAll((request("wall.get", mapOf("owner_id" to id, "offset" to offset.toString(), "count" to "100"))["items"] as JSONArray).toList())
        }
        return wall.map { it -> WallPost(it as HashMap<String, Any>)  }
    }

    fun like(type: String, own_id: String, item_id: String){
        request("likes.add", mapOf("type" to type, "owner_id" to own_id, "item_id" to item_id))
    }

    fun dislike(type: String, own_id: String, item_id: String){
        request("likes.delete", mapOf("type" to type, "owner_id" to own_id, "item_id" to item_id))
    }


}