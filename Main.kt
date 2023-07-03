package converter

fun main() {
    UnitConverter().work()
}

enum class Unit(val metric: String, val app: String, val names: List<String>, val toBase: (Double) -> Double, val fromBase: (Double) -> Double) {
    Meter("Length", "meter", listOf("m", "meters", "meter"), {it}, {it}),
    Kilometer("Length", "kilometer", listOf("km", "kilometers", "kilometer"), {it * 1000}, {it / 1000}),
    Centimeter("Length", "centimeter", listOf("cm", "centimeters", "centimeter"), {it * 0.01}, {it / 0.01}),
    Millimeter("Length", "millimeter", listOf("mm", "millimeters", "millimeter"), {it * 0.001}, {it / 0.001}),
    Mile("Length", "mile", listOf("mi", "miles", "mile"), {it * 1609.35}, {it/ 1609.35}),
    Yard("Length", "yard", listOf("yd", "yards", "yard"), {it * 0.9144}, {it / 0.9144}),
    Foot("Length", "foot", listOf("ft", "feet", "foot"), {it * 0.3048}, {it / 0.3048}),
    Inch("Length", "inch", listOf("in", "inches", "inch"), {it * 0.0254}, {it /  0.0254}),
    Gram("Weight", "gram", listOf("g", "grams", "gram"), {it}, {it}),
    Kilogram("Weight", "kilogram", listOf("kg", "kilograms", "kilogram"), {it * 1000}, {it / 1000}),
    Milligram("Weight", "milligram", listOf("mg", "milligrams", "milligram"), {it * 0.001}, {it / 0.001}),
    Pound("Weight", "pound", listOf("lb", "pounds", "pound"), {it * 453.592}, {it / 453.592}),
    Ounce("Weight", "ounce", listOf("oz", "ounces", "ounce"), {it * 28.3495}, {it / 28.3495}),
    DegreeCelsius("Temperature", "degree Celsius", listOf("degree celsius", "degrees celsius", "celsius", "dc", "c"), {it}, {it}),
    DegreeFahrenheit("Temperature", "degree Fahrenheit", listOf("degree fahrenheit", "degrees fahrenheit", "fahrenheit", "df", "f"), {(it- 32) * 5/9}, {it * 9/5 + 32}),
    Kelvin("Temperature", "kelvin", listOf("kelvin", "kelvins", "k"), {it - 273.15}, {it + 273.15}),
    Error("", "???", listOf(""), {0.0} , {0.0})
}
class UnitConverter {

    fun work() {
        while (true) {
            val input = println("Enter what you want to convert (or exit): ").run { readln().lowercase() }.split(" ")
            if (input[0] == "exit") break
            val metric1 =
                define(if (input[1] == "degree" || input[1] == "degrees") input[1] + " " + input[2] else input[1])
            val metric2 = define(
                when (input.size) {
                    4 -> input[3]
                    5 -> if (input[1] == "degree" || input[1] == "degrees") input[4] else input[3] + " " + input[4]
                    else -> input[4] + " " + input[5]
                }
            )
            if ((metric1.metric == "Length" || metric1.metric == "Weight") && input[0].toDouble() < 0.0) {
               println("${metric1.metric} shouldn't be negative")
            } else if (metric1.metric == metric2.metric && metric1.app != "???") {
                val count = metric2.fromBase(metric1.toBase(input[0].toDouble()))
                println(
                    "${input[0].toDouble()} " +
                            when (input[0].toDouble()) {
                                1.0 -> metric1.app
                                else -> amt(metric1.app)
                            } + " is " + "$count " +
                            when(count) {
                                1.0 -> metric2.app
                                else -> amt(metric2.app)
                            }
                )
            } else  if (metric1.metric != metric2.metric || metric1.metric == "" && input[0].toDoubleOrNull() != null){
                println("Conversion from ${if (metric1.app == "???") metric1.app else amt(metric1.app)} to ${if (metric2.app == "???") metric2.app else amt(metric2.app)} is impossible")
            } else println("Parse error")
        }
    }

    private fun define(metric: String): Unit {
        for (unit in Unit.values()) if (metric in unit.names) return unit
        return Unit.Error
    }

    private fun amt(metric: String): String {
        return when (metric) {
            "degree Celsius" -> "degrees Celsius"
            "degree Fahrenheit" -> "degrees Fahrenheit"
            "foot" -> "feet"
            "inch" -> "inches"
            else -> metric + "s"
        }
    }

}