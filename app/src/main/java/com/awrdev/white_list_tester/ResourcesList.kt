package com.awrdev.white_list_tester

data class WebResourcesList(
    val listName: String,
    val resourcesList: List<WebResource>
)

data class WebResource(
    val url: String,
    val name: String
)

object ResourcesList {
//    val WhiteList = arrayOf("https://yandex.ru", "https://ozon.ru", "https://max.ru")
    val WhiteList = WebResourcesList(
    "Белый список",
    listOf(
        WebResource("https://yandex.ru", "Яндекс"),
        WebResource("https://ozon.ru", "Озон"),
        WebResource("https://max.ru", "МАХ"),
        WebResource("https://vk.ru", "ВКонтакте"),
        WebResource("https://rbc.ru", "РБК"),
        WebResource("https://kommersant.ru", "Коммерсант"),
        WebResource("https://rutube.ru", "RUTUBE"),
        WebResource("https://wildberries.ru", "Wildberries"),
        ))
//    val RussianWebsites = arrayOf("https://74.ru", "https://pikabu.ru", "https://sberbank.ru")
    val RussianWebsites = WebResourcesList(
        "Российские сайты",
        listOf(
            WebResource("https://74.ru", "74.ру"),
            WebResource("https://pikabu.ru", "Пикабу"),
            WebResource("https://sberbank.ru", "Сбербанк"),
            WebResource("https://tbank.ru", "Т-Банк"),
            WebResource("https://stack-it.ru", "СТЕК ЖКХ"),
            WebResource("https://m-e-c.ru", "МЭК"),
            WebResource("https://rp5.ru", "РП5"),
        ))

//    val ForeignWebsites = arrayOf("https://google.com", "https://reddit.com", "https://bbc.co.uk")
    val ForeignWebsites = WebResourcesList(
        "Зарубежные сайты",
        listOf(
            WebResource("https://google.com", "Google"),
            WebResource("https://reddit.com", "Reddit"),
            WebResource("https://bbc.co.uk", "BBC"),
            WebResource("https://wikipedia.org", "Wikipedia"),
            WebResource("https://microsoft.com", "Microsoft"),
            WebResource("https://github.com", "GitHub"),
            WebResource("https://habr.com", "Habr"),
            WebResource("https://cloudflare.com", "Cloudflare"),
            WebResource("https://amazon.com", "Amazon"),
        ))
//    val BannedWebsites = arrayOf("https://youtube.com", "https://x.com", "https://facebook.com")
    val BannedWebsites = WebResourcesList(
        "Заблокированные сайты",
        listOf(
            WebResource("https://youtube.com", "YouTube"),
            WebResource("https://x.com", "X (Twitter)"),
            WebResource("https://facebook.com", "Facebook"),
            WebResource("https://telegram.org", "Telegram"),
            WebResource("https://whatsapp.com", "WhatsApp"),
        ))
}
