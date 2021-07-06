import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Player (
            private var teamFK: Int?=null,
            private var identification: String?=null,
            private var name: String?=null,
            private var gender: Char?=null,
            private var weight: Double?=null,
            private var height: Double?=null,
            private var age: Int?=null,
            private var bornDate: LocalDate?=null,
        ) {

    public fun setTeamFK(teamFK: Int?=null):Unit {
        this.teamFK=teamFK
    }

    public fun getTeamFK():Int? {
        return this.teamFK
    }

    public fun setIdentification(identification: String?=null):Unit {
        this.identification=identification
    }

    public fun getIdentification():String? {
        return this.identification
    }

    public fun setName(name: String?=null):Unit {
        this.name=name
    }

    public fun getiName():String? {
        return this.name
    }

    public fun setGender(gender: Char?=null):Unit {
        this.gender=gender
    }

    public fun getGender():Char? {
        return this.gender
    }

    public fun setWeight(weight: Double?=null):Unit {
        this.weight=weight
    }

    public fun getWeight():Double? {
        return this.weight
    }

    public fun setHeight(height: Double?=null):Unit {
        this.height=height
    }

    public fun getHeight():Double? {
        return this.height
    }

    public fun setAge(age: Int?=null):Unit {
        this.age=age
    }

    public fun getAge():Int? {
        return this.age
    }

    public fun setBornDate(bornDate: LocalDate?=null):Unit {
        this.bornDate=bornDate
    }

    public fun getbornDate():LocalDate? {
        return this.bornDate
    }

    override fun toString(): String {
        return this.teamFK.toString()+","+this.identification.toString()+","+this.name+","+this.gender.toString()+","+this.weight.toString()+","+
                this.height.toString()+","+this.age.toString()+","+this.bornDate?.format(
            DateTimeFormatter.ofPattern("dd-MM-yyyy"))+"\n"
    }

}