package example

import java.io.File
import java.nio.charset.Charset

data class QinggeItem(val code: String, val words: List<String>)

private val luogeDictFile = File("data/luoge-main-dict.txt")
private val qinggeTargetFile = File("data/generated/xiaohe_table.txt")

fun main(args: Array<String>) {
    val luogeItems = readWordCodeFromLuoge(luogeDictFile)
    val qinggeItems = luogeItems.groupBy { it.code }.mapValues { items ->
        items.value.sortedBy { it.index }
    }.map { (code, words) ->
        QinggeItem(code, words.map { it.word })
    }.sortedBy { it.code }

    qinggeTargetFile.writeText(
            qinggeItems.joinToString("\n") { "${it.code} ${it.words.joinToString(" ")}" },
            Charset.forName("UTF-8")
    )
}

private fun readWordCodeFromLuoge(file: File): List<LuogeItem> {
    return file.readLines(Charset.forName("UTF-16")).filterNot { it.isEmpty() }
            .map { line ->
                val (word, code, index) = line.split("\t")
                LuogeItem(word, code, index.toInt())
            }
}

data class LuogeItem(val word: String, val code: String, val index: Int)