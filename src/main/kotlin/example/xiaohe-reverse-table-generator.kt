package example

import github.freewind.xiaohe.chardecoder.XiaoHeCharDecoder
import java.io.File
import java.nio.charset.Charset

private val qinggeReverseFile = File("data/generated/xiaohe_reverse_table.txt")

fun main(args: Array<String>) {
    val others = run {
        val popularChars = XiaoHeCharDecoder.popularCode.map { it.char }
        XiaoHeCharDecoder.charsAll.filterNot { popularChars.contains(it.char) }
    }
    val items = (XiaoHeCharDecoder.popularCode.map { ReverseTableItem(it.char, it.code) } + others.map { ReverseTableItem(it.char, it.longestCodes.first()) })
            .sortedBy { it.code }

    qinggeReverseFile.writeText(
            items.joinToString("\n") { (char, code) -> "$char $code" },
            Charset.forName("UTF-8")
    )
}

data class ReverseTableItem(val char: Char, val code: String)