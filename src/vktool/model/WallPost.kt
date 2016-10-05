package vktool.model

import vktool.APIHelper
import java.util.*

/**
 * Created by bigz on 05.10.16.
 */
class WallPost(json: HashMap<String, Any>) {
    val id = (json["id"]as Int).toString()
    val ownerId = (json["owner_id"] as Int).toString()
    val fromId = (json["from_id"] as Int).toString()
    val date = (json["date"] as Int).toString()
    val text = json["text"] as String
    val replyOwnerId = (json["reply_owner_id"] as Int?).toString()
    val replyPostId = (json["reply_post_id"] as Int?).toString()
    val friendsOnly = json["friend_only"] as String?
    val comments = Comments((json["comments"] as HashMap<String, Any>)["count"] as Int, if ((json["comments"] as HashMap<String, Any>)["can_post"] == 1) true else false)

    data class Comments(
            var count: Int = 0,
            var canPost: Boolean = false
    )

    data class Likes(
            var count: Int = 0,
            var userLikes: Int,
            var canLike: Int,
            var canPublish: Int
    )

    val likes = Likes((json["likes"] as HashMap<String, Any>)["count"] as Int, (json["likes"] as HashMap<String, Any>)["user_likes"] as Int, (json["likes"] as HashMap<String, Any>)["can_like"] as Int, (json["likes"] as HashMap<String, Any>)["can_publish"] as Int)
    //TODO: Остальное

    fun like(token: String){
        APIHelper(token).like("post", ownerId, id)
    }
    fun dislike(token: String){
        APIHelper(token).dislike("post", ownerId, id)
    }
}