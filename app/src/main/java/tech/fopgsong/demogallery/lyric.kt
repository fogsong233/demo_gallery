package tech.fopgsong.demogallery

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class LyricInfo(
    val originText: String,
    val viceText: String,
    val duration: Duration = 3.seconds
)


val testLyrics = listOf(
    LyricInfo("Her green plastic watering can", "她那绿色的塑料洒水壶"),
    LyricInfo("For her fake Chinese rubber plant", "是为了她那株假的中国橡胶植物"),
    LyricInfo("In the fake plastic earth", "种在假塑料土壤里"),

    LyricInfo("That she bought from a rubber man", "她从一个橡胶人那里买来的"),
    LyricInfo("In a town full of rubber plans", "那个城镇满是橡胶计划"),
    LyricInfo("To get rid of itself", "为的是摆脱自己"),

    LyricInfo("It wears her out", "这让她筋疲力尽"),
    LyricInfo("It wears her out", "这让她疲惫不堪"),

    LyricInfo("She lives with a broken man", "她和一个破碎的人一起生活"),
    LyricInfo("A cracked polystyrene man", "一个裂痕斑斑的聚苯乙烯人"),
    LyricInfo("Who just crumbles and burns", "他只会崩溃与燃烧"),

    LyricInfo("He used to do surgery", "他曾经是做手术的"),
    LyricInfo("For girls in the eighties", "为80年代的女孩们"),
    LyricInfo("But gravity always wins", "但最终重力总会胜利"),

    LyricInfo("It wears him out", "这让他精疲力尽"),
    LyricInfo("It wears him out", "这让他心力交瘁"),

    LyricInfo("She looks like the real thing", "她看起来就像真的"),
    LyricInfo("She tastes like the real thing", "她尝起来也像真的"),
    LyricInfo("My fake plastic love", "我那虚假的塑料爱情"),

    LyricInfo("But I can't help the feeling", "但我无法抑制那种感觉"),
    LyricInfo("I could blow through the ceiling", "我仿佛能穿破天花板"),
    LyricInfo("If I just turn and run", "如果我只是一转身然后逃跑"),

    LyricInfo("It wears me out", "这让我筋疲力尽"),
    LyricInfo("It wears me out", "这让我疲惫不堪"),

    LyricInfo("If I could be who you wanted", "如果我能成为你想要的那个人"),
    LyricInfo("If I could be who you wanted", "如果我能成为你想要的那个人"),
    LyricInfo("All the time", "一直一直")
)
val newLyrics = listOf(
    LyricInfo("彼女の人生は金色，", "她的人生 金光熠熠", 3.seconds),
    LyricInfo("真っ白な照明の下で、、", "在纯白的灯光之下", 3.seconds),
    LyricInfo("きらきらと反射した，", "闪烁着反射出", 3.seconds),
    LyricInfo("きらきらと反射した光，", "闪烁着反射出光芒", 3.seconds),
    LyricInfo("私はテレビゲームで，", "在玩电子游戏时", 3.seconds),
    LyricInfo("痛い目を擦っては，", "揉了揉酸痛的眼睛", 3.seconds),
    LyricInfo("夢の中でもまだ地球を、、", "梦中出现了地球", 3.seconds),
    LyricInfo("地球を守って戦ってる，", "为了保护地球而奋战着", 4.seconds),

    LyricInfo("満足してるよ、、", "对于人生的绝大部分", 3.seconds),
    LyricInfo("人生の大体の部分では，", "我已经心满意足了", 3.seconds),
    LyricInfo("でも少し、あと少しの安心が欲しい，", "但我还想再心安惬意一些", 4.seconds),
    LyricInfo("窓際で眠る猫みたいに，", "就像倚窗安眠的猫咪一样", 3.seconds),
    LyricInfo("陽だまりに溶けてゆく毎日が欲しい，", "每一天 都梦想着在日光下融化", 4.seconds),

    LyricInfo("彼女の運命は金色，", "她的命运 金光熠熠", 3.seconds),
    LyricInfo("バックステージでも輝きをやめず、、", "即便是在后台 也不停闪烁着", 3.seconds),
    LyricInfo("きらきらと放射して，", "闪烁着发出光芒", 3.seconds),
    LyricInfo("きらきらと絶え間なく光る，", "闪烁不止地散发光芒", 4.seconds),

    LyricInfo("はずでもないってことくらい，", "虽然在脑子里很清楚", 3.seconds),
    LyricInfo("頭ではわかっているけれど，", "不应该去这样", 3.seconds),
    LyricInfo("じりじりと羨んで、、", "她无比的羡慕着", 3.seconds),
    LyricInfo("羨んでまた見失ってる，", "羡慕中 又陷入了迷失", 3.seconds),

    LyricInfo("満足してるよ、、", "对于人生的绝大部分", 3.seconds),
    LyricInfo("人生の大体の部分では，", "我已经心满意足了", 3.seconds),
    LyricInfo("でも少し、あと少しの説明が欲しい，", "但我还是想 多加解释说明一下", 4.seconds),
    LyricInfo("窓際で眠る猫みたいに，", "就像倚窗安眠的猫咪一样", 3.seconds),
    LyricInfo("陽だまりに溶けてゆく毎日が欲しい，", "每一天 都梦想着在日光下融化", 4.seconds),

    LyricInfo("満足してるよ、、", "对于人生的绝大部分", 3.seconds),
    LyricInfo("人生の大体の部分では，", "我已经心满意足了", 3.seconds),
    LyricInfo("でも少し、あと少しの安心が欲しい，", "但我还想再心安惬意一些", 4.seconds),
    LyricInfo("平気だよって誤魔化すのは，", "用一句 \"我没事\" 就搪塞过去", 3.seconds),
    LyricInfo("もうやめにしてみたい，", "我已经想放弃", 3.seconds),

    LyricInfo("今日もここで生きる君と，", "与当下正活在我面前的你", 3.seconds),
    LyricInfo("ちゃんと話がしたい，", "来一段正式的交谈", 4.seconds),
)
