package vktool

import java.io.File
import java.security.SecureRandom

/**
 * Created by bigz on 04.10.16.
 */

var token: String = ""

fun main(args: Array<String>) {
    //println(args.size)
    token = File("token.tkn").readLines().first()
    println(token)
    if (args.size == 0) {
        println("Need taret id")
        return;
    }

    val api = APIHelper(token)
    val id = api.getId(args[0])
    likeAllWall(id)
    dislikeAllWall(id)
}

fun likeAllWall(id: String) {
    val api = APIHelper(token)
    val wall = api.getAllWall(id)
    val rand = SecureRandom()
    wall.forEach {
        it.like(token)
        Thread.sleep((Math.abs((rand.nextLong() % 4)) + 2) * 1000)
    }
}

fun dislikeAllWall(id: String) {
    val api = APIHelper(token)
    val wall = api.getAllWall(id)
    val rand = SecureRandom()
    wall.forEach {
        it.dislike(token)
        Thread.sleep((Math.abs((rand.nextLong() % 4)) + 2) * 1000)
    }
}