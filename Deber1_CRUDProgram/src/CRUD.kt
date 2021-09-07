import java.io.*
import java.time.LocalDate


class CRUD {

    fun createTeam() {
        val objTeam = Team()
        print("Enter the new team's identification number: ")
        objTeam.setIdentification(readLine()!!.toInt())
        print("Enter the new team's name: ")
        objTeam.setName(readLine()!!.toString())
        print("Enter 1 if team is active and 0 if it's not: ")
        when (readLine()!!.toInt()) {
            0 -> objTeam.setActive(false)
            1 -> objTeam.setActive(true)
        }
        print("Enter the new team's actual number of players: ")
        objTeam.setNumPlayers(readLine()!!.toInt())
        print("Enter the new team's manager's name: ")
        objTeam.setManager(readLine()!!.toString())
        print("Enter the new team's creation's date in format dd/mm/yyyy: ")
        val date = readLine()!!.toString()
        val datArray = date.split("/") as ArrayList<String>
        val year = datArray[2].toInt()
        val month = datArray[1].toInt()
        val day = datArray[0].toInt()
        objTeam.setCreationDate(LocalDate.of(year,month,day))

        val file = File("src/TeamsList.txt")
        file.appendText(objTeam.toString())
        println()

    }

    fun readTeam() {
        val teamList = arrayListOf<Team>()
        val stream: InputStream = File("src/TeamsList.txt").inputStream()
        stream.bufferedReader().useLines {
                lines ->
            lines.forEach {
                val teamLine = it.split(",")
                teamList.add(
                    Team(
                        teamLine[0].toInt(),
                        teamLine[1],
                        teamLine[2].toInt()==1,
                        teamLine[3].toInt(),
                        teamLine[4],
                        LocalDate.of(teamLine[5].split("-")[2].toInt(),teamLine[5].split("-")[1].toInt(),teamLine[5].split("-")[0].toInt())
                    )
                )
            }
        }

        print("\n LIST OF TEAMS\n")
        teamList.forEach{
            print(it.toString())
        }
        println()
    }

    fun updateTeam() {
        val teamList = arrayListOf<Team>()
        val stream: InputStream = File("src/TeamsList.txt").inputStream()
        stream.bufferedReader().useLines {
                lines ->
            lines.forEach {
                val teamLine = it.split(",")
                teamList.add(
                    Team(
                        teamLine[0].toInt(),
                        teamLine[1],
                        teamLine[2].toInt()==1,
                        teamLine[3].toInt(),
                        teamLine[4],
                        LocalDate.of(teamLine[5].split("-")[2].toInt(),teamLine[5].split("-")[1].toInt(),teamLine[5].split("-")[0].toInt())
                    )
                )
            }
        }

        print("Enter the identification number of the player to modify: ")
        val teamIdentification = readLine()!!.toInt()
        teamList.forEach {
            if (it.getIdentification()==teamIdentification) {
                println("The player to update contains the following information")
                println(it.toString())

                println("Enter 1 to modify the team's identification")
                println("Enter 2 to modify the team's name")
                println("Enter 3 to modify the team's state")
                println("Enter 4 to modify the team's number of players")
                println("Enter 5 to modify the team's manager")
                println("Enter 6 to modify the team's creation date")
                when (readLine()!!.toInt()) {
                    1 -> {
                        println("Enter the team's new identification number: ")
                        it.setIdentification(readLine()!!.toInt())
                    }
                    2 -> {
                        println("Enter the team's new name: ")
                        it.setName(readLine()!!.toString())
                    }
                    3 -> {
                        println("Enter the team's new state (1 if the team is active or 0 if it is not active): ")
                        when (readLine()!!.toInt()) {
                            0 -> it.setActive(false)
                            1 -> it.setActive(true)
                        }
                    }
                    4 -> {
                        println("Enter the team's new number of players: ")
                        it.setNumPlayers(readLine()!!.toInt())
                    }
                    5 -> {
                        println("Enter the team's new manager: ")
                        it.setManager(readLine()!!.toString())
                    }
                    6 -> {
                        println("Enter the player's new height (use dot to separate decimals): ")
                        val date = readLine()!!.toString()
                        val datArray = date.split("/") as ArrayList<String>
                        val year = datArray[2].toInt()
                        val month = datArray[1].toInt()
                        val day = datArray[0].toInt()
                        it.setCreationDate(LocalDate.of(year,month,day))
                    }
                }
            }
        }

        val filePrintWriter = PrintWriter(File("src/PlayersList.txt"))
        filePrintWriter.close()

        teamList.forEach {
            File("src/PlayersList.txt").appendText(it.toString())
        }

        println()

    }

    fun deleteTeam() {
        print("Enter the identification number of the team to delete: ")
        val teamIdentification = readLine()!!.toInt()

        val teamList = arrayListOf<Team>()
        val stream: InputStream = File("src/TeamsList.txt").inputStream()
        stream.bufferedReader().useLines {
                lines ->
            lines.forEach {
                val teamLine = it.split(",")
                if (teamLine[0].toInt() != teamIdentification) {
                    teamList.add(
                        Team(
                            teamLine[0].toInt(),
                            teamLine[1],
                            teamLine[2].toInt()==1,
                            teamLine[3].toInt(),
                            teamLine[4],
                            LocalDate.of(teamLine[5].split("-")[2].toInt(),teamLine[5].split("-")[1].toInt(),teamLine[5].split("-")[0].toInt())
                        )
                    )
                }
            }
        }
        val filePrintWriter = PrintWriter(File("src/TeamsList.txt"))
        filePrintWriter.close()

        teamList.forEach {
            File("src/TeamsList.txt").appendText(it.toString())
        }

        println()

    }

    fun createPlayer() {
        val objPlayer = Player()
        print("Enter the new player's team identification number: ")
        objPlayer.setTeamFK(readLine()!!.toInt())
        print("Enter the new player's identification number: ")
        objPlayer.setIdentification(readLine()!!.toString())
        print("Enter the new player's name: ")
        objPlayer.setName(readLine()!!.toString())
        print("Enter M to set player's gender as Male and F to set player's gender as Female: ")
        objPlayer.setGender(readLine()!!.toString()[0])
        print("Enter the new player's weight in kgs (use dot to separate decimals): ")
        objPlayer.setWeight(readLine()!!.toDouble())
        print("Enter the new player's height in meters (use dot to separate decimals): ")
        objPlayer.setHeight(readLine()!!.toDouble())
        print("Enter the new player's age: ")
        objPlayer.setAge(readLine()!!.toInt())
        print("Enter the new player's born date in format dd/mm/yyyy: ")
        val date = readLine()!!.toString()
        val datArray = date.split("/") as ArrayList<String>
        val year = datArray[2].toInt()
        val month = datArray[1].toInt()
        val day = datArray[1].toInt()
        objPlayer.setBornDate(LocalDate.of(year,month,day))

        val file = File("src/PlayersList.txt")
        file.appendText(objPlayer.toString())

        println()

    }

    fun readPlayer() {
        val playerList = arrayListOf<Player>()
        val stream: InputStream = File("src/PlayersList.txt").inputStream()
        stream.bufferedReader().useLines {
                lines ->
            lines.forEach {
                val playerLine = it.split(",")
                playerList.add(
                    Player(
                        playerLine[0].toInt(),
                        playerLine[1],
                        playerLine[2],
                        playerLine[3][0],
                        playerLine[4].toDouble(),
                        playerLine[5].toDouble(),
                        playerLine[6].toInt(),
                        LocalDate.of(playerLine[7].split("-")[2].toInt(),playerLine[7].split("-")[1].toInt(),playerLine[7].split("-")[0].toInt())
                    )
                )
            }
        }

        print("\n LIST OF TEAMS\n")
        playerList.forEach{
            print(it.toString())
        }
        println()
    }

    fun updatePlayer() {
        val playerList = arrayListOf<Player>()
        val stream: InputStream = File("src/PlayersList.txt").inputStream()
        stream.bufferedReader().useLines {
                lines ->
            lines.forEach {
                val playerLine = it.split(",")
                playerList.add(
                    Player(
                        playerLine[0].toInt(),
                        playerLine[1],
                        playerLine[2],
                        playerLine[3][0],
                        playerLine[4].toDouble(),
                        playerLine[5].toDouble(),
                        playerLine[6].toInt(),
                        LocalDate.of(playerLine[7].split("-")[2].toInt(),playerLine[7].split("-")[1].toInt(),playerLine[7].split("-")[0].toInt())
                    )
                )
            }
        }

        print("Enter the identification number of the player to modify: ")
        val playerIdentification = readLine()!!.toString()
        playerList.forEach {
            if (it.getIdentification()==playerIdentification) {
                println("The player to update contains the following information")
                println(it.toString())

                println("Enter 1 to modify the player's team")
                println("Enter 2 to modify the player's identification")
                println("Enter 3 to modify the player's name")
                println("Enter 4 to modify the player's gender")
                println("Enter 5 to modify the player's weight")
                println("Enter 6 to modify the player's height")
                println("Enter 7 to modify the player's age")
                println("Enter 8 to modify the player's born date")
                when (readLine()!!.toInt()) {
                    1 -> {
                        println("Enter the player's new team identification number: ")
                        it.setTeamFK(readLine()!!.toInt())
                    }
                    2 -> {
                        println("Enter the player's new identification number: ")
                        it.setIdentification(readLine()!!.toString())
                    }
                    3 -> {
                        println("Enter the player's new name: ")
                        it.setName(readLine()!!.toString())
                    }
                    4 -> {
                        println("Enter the player's new gender (M for Male or F for Female): ")
                        it.setGender(readLine()!!.toString()[0])
                    }
                    5 -> {
                        println("Enter the player's new weight (use dot to separate decimals): ")
                        it.setWeight(readLine()!!.toDouble())
                    }
                    6 -> {
                        println("Enter the player's new height (use dot to separate decimals): ")
                        it.setHeight(readLine()!!.toDouble())
                    }
                    7 -> {
                        println("Enter the player's new age: ")
                        it.setAge(readLine()!!.toInt())
                    }
                    8 -> {
                        println("Enter the player's born date (dd/mm/yyyy): ")
                        val date = readLine()!!.toString()
                        val datArray = date.split("/") as ArrayList<String>
                        val year = datArray[2].toInt()
                        val month = datArray[1].toInt()
                        val day = datArray[1].toInt()
                        it.setBornDate(LocalDate.of(year,month,day))
                    }
                }
            }
        }

        val filePrintWriter = PrintWriter(File("src/PlayersList.txt"))
        filePrintWriter.close()

        playerList.forEach {
            File("src/PlayersList.txt").appendText(it.toString())
        }

        println()

    }

    fun deletePlayer() {
        print("Enter the identification number of the player to delete: ")
        val playerIdentification = readLine()!!.toString()

        val playerList = arrayListOf<Player>()
        val stream: InputStream = File("src/PlayersList.txt").inputStream()
        stream.bufferedReader().useLines {
                lines ->
            lines.forEach {
                val playerLine = it.split(",")
                if (playerLine[1] != playerIdentification) {
                    playerList.add(
                        Player(
                            playerLine[0].toInt(),
                            playerLine[1],
                            playerLine[2],
                            playerLine[3][0],
                            playerLine[4].toDouble(),
                            playerLine[5].toDouble(),
                            playerLine[6].toInt(),
                            LocalDate.of(playerLine[7].split("-")[2].toInt(),playerLine[7].split("-")[1].toInt(),playerLine[7].split("-")[0].toInt())
                        )
                    )
                }
            }
        }
        val filePrintWriter = PrintWriter(File("src/PlayersList.txt"))
        filePrintWriter.close()

        playerList.forEach {
            File("src/PlayersList.txt").appendText(it.toString())
        }

        println()

    }

}