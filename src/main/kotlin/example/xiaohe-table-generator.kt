package example

import github.freewind.xiaohe.chardecoder.XiaoHeCharDecoder
import java.io.File
import java.nio.charset.Charset

data class QinggeItem(val code: String, val words: List<String>)

private val luogeDictFile = File("data/luoge-main-dict.txt")
private val qinggeTargetFile = File("data/generated/xiaohe_table.txt")

fun main(args: Array<String>) {
    val luogeItems = readWordCodeFromLuoge(luogeDictFile)
    val charItems = run {
        generateFromChars().filterNot { (word, code) -> luogeItems.any { it.word == word && it.code == code } }
    }
    val qinggeItems = (luogeItems + charItems)
            .groupBy { it.code }
            .mapValues { it.value.sortedBy { it.index } }
            .map { (code, items) -> QinggeItem(code, items.map { it.word }) }
            .sortedBy { it.code }
    qinggeTargetFile.writeText(
            qinggeItems.joinToString("\n") { "${it.code} ${it.words.joinToString(" ")}" },
            Charset.forName("UTF-8")
    )
}

fun generateFromChars(): List<LuogeItem> {
    return XiaoHeCharDecoder.charsAll.map { charInfo ->
        val codes = charInfo.longestCodes.map { code -> generateShortCodes(code) }.flatten()
        codes.map { LuogeItem(charInfo.char.toString(), it, Integer.MAX_VALUE) }
    }.flatten()
}

fun generateShortCodes(code: String): List<String> {
    return (2..code.length).map { index -> code.substring(0, index) }
}

private fun readWordCodeFromLuoge(file: File): List<LuogeItem> {
    return file.readLines(Charset.forName("UTF-16")).filterNot { it.isEmpty() }
            .map { line ->
                val (word, code, index) = line.split("\t")
                LuogeItem(word, code, index.toInt())
            }
}

data class LuogeItem(val word: String, val code: String, val index: Int)