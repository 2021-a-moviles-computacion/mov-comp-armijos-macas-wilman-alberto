import kotlin.system.exitProcess

fun main() {
    println("--------------------------------------Welcome to CRUD program--------------------------------------")
    menu()
}

fun menu() {
    println("Enter 1 to CREATE a new OBJECT into the system")
    println("Enter 2 to READ an OBJECT from the system")
    println("Enter 3 to UPDATE an OBJECT from the system")
    println("Enter 4 to DELETE an OBJECT from the system")
    println("Enter 0 to EXIT")
    when (readLine()!!.toInt()) {
        0 -> exitProcess(0)
        1 -> createMenu()
        2 -> readMenu()
        3 -> updateMenu()
        4 -> deleteMenu()
        else -> {
            println("Enter a valid option, please")
            println()
            menu()
        }
    }
}

fun createMenu() {
    println("Enter 1 to CREATE a new TEAM")
    println("Enter 2 to CREATE a new PLAYER")
    println("Enter 0 to EXIT")
    when (readLine()!!.toInt()) {
        0 -> exitProcess(0)
        1 -> CRUD().createTeam()
        2 -> CRUD().createPlayer()
        else -> {
            println("Enter a valid option, please")
            println()
            createMenu()
        }
    }
    menu()
}

fun readMenu() {
    println("Enter 1 to READ a TEAM")
    println("Enter 2 to READ a PLAYER")
    println("Enter 0 to EXIT")
    when (readLine()!!.toInt()) {
        0 -> exitProcess(0)
        1 -> CRUD().readTeam()
        2 -> CRUD().readPlayer()
        else -> {
            println("Enter a valid option, please")
            println()
            readMenu()
        }
    }
    menu()
}

fun updateMenu() {
    println("Enter 1 to UPDATE a TEAM")
    println("Enter 2 to UPDATE a PLAYER")
    println("Enter 0 to EXIT")
    when (readLine()!!.toInt()) {
        0 -> exitProcess(0)
        1 -> CRUD().updateTeam()
        2 -> CRUD().updatePlayer()
        else -> {
            println("Enter a valid option, please")
            println()
            updateMenu()
        }
    }
    menu()
}

fun deleteMenu() {
    println("Enter 1 to DELETE a TEAM")
    println("Enter 2 to DELETE a PLAYER")
    println("Enter 0 to EXIT")
    when (readLine()!!.toInt()) {
        1 -> CRUD().deleteTeam()
        2 -> CRUD().deletePlayer()
        else -> {
            println("Enter a valid option, please")
            println()
            deleteMenu()
        }
    }
    menu()
}