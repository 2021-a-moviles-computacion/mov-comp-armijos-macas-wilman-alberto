import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Team(
    private var identification: Int? = null,
    private var name: String? = null,
    private var active: Boolean? = null,
    private var numPlayers: Int? = null,
    private var manager: String? = null,
    private var creationDate: LocalDate? = null,
) {


    public fun setIdentification(identification: Int?=null):Unit {
        this.identification=identification
    }

    public fun getIdentification():Int? {
        return this.identification
    }

    public fun setName(name: String?=null):Unit {
        this.name=name
    }

    public fun getName():String? {
        return this.name
    }

    public fun setActive(active: Boolean?=null):Unit {
        this.active=active
    }

    public fun isActive():Boolean? {
        return this.active
    }

    public fun setNumPlayers(numPlayer: Int?=null):Unit {
        this.numPlayers=numPlayer
    }

    public fun getNumPlayers():Int? {
        return this.numPlayers
    }

    public fun setManager(manager: String?=null):Unit {
        this.manager=manager
    }

    public fun getManager():String? {
        return this.manager
    }

    public fun setCreationDate(creationDate: LocalDate?=null):Unit {
        this.creationDate=creationDate
    }

    public fun getCreationDate():LocalDate? {
        return this.creationDate
    }

    public override fun toString(): String {
        return this.identification.toString()+","+this.name+","+(if (this.active == true) "1" else "0")+","+this.numPlayers.toString()+","+this.manager+","+this.creationDate?.format(
            DateTimeFormatter.ofPattern("dd-MM-yyyy")) +"\n"
    }

}