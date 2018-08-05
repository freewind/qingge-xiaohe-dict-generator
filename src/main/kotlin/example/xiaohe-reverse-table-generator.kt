package example

import github.freewind.xiaohe.chardecoder.XiaoHeCharDecoder
import java.io.File
import java.nio.charset.Charset

private val qinggeReverseFile = File("data/generated/xiaohe_reverse_table.txt")

fun main(args: Array<String>) {
    val items = XiaoHeCharDecoder.charsAll.map {
        ReverseTableItem(it.char, it.longestCode)
    }.sortedBy { it.code }
    qinggeReverseFile.writeText(
            items.joinToString("\n") { (char, code) -> "$char $code" },
            Charset.forName("UTF-8")
    )
}

data class ReverseTableItem(val char: Char, val code: String)