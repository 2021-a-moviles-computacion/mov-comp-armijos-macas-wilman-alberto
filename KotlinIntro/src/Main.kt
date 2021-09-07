fun main() {
    println("Hola Mundo")
    // Para una variable se puede trabajar de la forma:
    // var "nombre", Ejemplo:
    var edadProfesor = 32

    // O tenemos la otra forma
    // var "sueldoProfesor": "Tipo"
    var sueldoProfesor: Int = 32



    // Variables mutables
    var edadCachorro: Int = 0
    edadCachorro = 1
    edadCachorro = 2


    // Variables inmutables
    val numCedula = "0706856747"
    //numCedula = "1709235877"

    if (true) {
        // sentencias verdaderas
    }
    else {
        // sentencias falsas
    }

    val estadoCivil: String = "S"
    when (estadoCivil) {
        ("S") ->  {
            // Condiciones si S
        }
        ("C") -> {
            // Condiciones si C
        }
        ("UL") -> println("UL")
        else -> {
            //Cuando no sea ninguno
        }
    }


    val mensaje = if (estadoCivil == "S") "SI" else "NO"


    calcularSueldo(100.00)
    calcularSueldo(100.00, 14.00)



    // parametros nombrados
    calcularSueldo(sueldo = 100.00, bonoEspecial = 15.00)


    val arregloEstatico: Array<Int> = arrayOf(1, 2, 3)
    println(arregloEstatico)

    val arregloDinamico: ArrayList<Int> = arrayListOf(1, 2, 3)
    arregloDinamico.add(4)
    arregloDinamico.add(5)
    println(arregloDinamico)

    println()
    println("FOR EACH")
    val respuestaForEach: Unit = arregloDinamico.forEach {
        println("$it")
    }

    println(respuestaForEach)


    println()
    println("FOR EACH 2")
    val respuestaForEach2: Unit = arregloDinamico.forEachIndexed {
            indice: Int,
            valorActual: Int ->
        println("El valor es ${valorActual} y tiene el indice ${indice}")
    }

    println(respuestaForEach2)



    val respuestaMAP: List<Double> = arregloDinamico.map {
        return@map it*it.toDouble()
    }

    println("MAP")
    println(respuestaMAP)


    val respuestaFILTER: List<Int> = arregloDinamico.filter {
        return@filter it>=4
    }

    println("FILTER")
    println(respuestaFILTER)


    val respuestaAny: Boolean = arregloDinamico.any {
        return@any (it > 2)
    }
    println("Si alguno cumple:")
    println(respuestaAny)


    val respuestaAll: Boolean = arregloDinamico.all {
        return@all (it > 2)
    }
    println("Si todos cumplen")
    println(respuestaAll)



    // REDUCE
    // arreglo={1, 2, 3, 4, 5}
    // acumulado = 0
    // acumulado = acumulado(0) + 1
    // acumulado = acumulado(1) + 2
    // acumulado = acumulado(3) + 3
    // acumulado = acumulado(6) + 4
    // acumulado = acumulado(10) + 5
    // acumulado = 15 (valor devuelto)


    val respuestaReduce: Int = arregloDinamico.reduce {
        acumulado: Int, valorActual: Int->
        return@reduce acumulado+valorActual
    }
    println("REDUCE - SUMA")
    println(respuestaReduce)


    val arregloDanio = arrayListOf<Int>(12, 15, 8, 10)

    val respuestaFold: Int = arregloDanio.fold(100) {
        acumulado: Int, valorActual: Int->
        return@fold acumulado-valorActual
    }
    println("FOLD - RESTA")
    println(respuestaFold)


    abstract class numerosJAVA {
        protected val numeroUNO: Int
        private val numeroDOS: Int
        constructor(
            uno: Int,
            dos: Int
        ) {
            numeroUNO=uno
            numeroDOS=dos
        }
    }

    val ejemploUno = suma(1, 2)
    val ejemploDos = suma(null, 2)
    val ejemploTres = suma(1, null)

    println("Suma 1 + 2")
    println(ejemploUno.sumar())
    println("Primer resultado del objeto companion")
    println(suma.historialSumas)
    println("Suma null + 2")
    println(ejemploDos.sumar())
    println("Segundo resultado del objeto companion")
    println(suma.historialSumas)
    println("Suma 1 + null")
    println(ejemploTres.sumar())
    println("Tercero resultado del objeto companion")
    println(suma.historialSumas)



}


abstract class numeros(
    protected var numeroUNO: Int,
    protected var numeroDOS: Int
) {
    init {
        println("Inicializar")

    }
}

class suma(
    uno: Int,
    dos: Int
): numeros(uno, dos) { //Se simula el constructor papá al heredar la clase numeros
    init {
        println("Inicializado")
    }
    constructor(
        uno: Int?,
        dos: Int
    ): this(uno ?: 0, dos)
    constructor(
        uno: Int,
        dos: Int?
    ): this(uno, dos ?: 0)

    fun sumar(): Int {
        agregarHistorial(numeroUNO + numeroDOS)
        return numeroUNO + numeroDOS
    }

    // SINGLETON
    companion object {
        val historialSumas = arrayListOf<Int>()

        fun agregarHistorial(nuevoValor: Int) {
            historialSumas.add(nuevoValor)
            println(historialSumas)
        }
    }


}



// fun "nombre funcion"("tipo de variable": "Nombre variable"):"tipo de retorno"
fun imprimirNombre(nombre: String):Unit {
    print("Nombre: ${nombre}")
}


fun calcularSueldo(
    sueldo: Double,
    tasa: Double = 12.00,
    bonoEspecial: Double? = null
): Double {
    return if (bonoEspecial == null) sueldo*100/tasa else sueldo*100/tasa+bonoEspecial
}







