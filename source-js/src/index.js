import cheerio from "cheerio";
// import request from 'sync-request';
//
//
// function log(message) {
//     console.log(message)
// }
//
// function fetch(url, headers, body) {
//     let method = body ? "POST" : "GET"
//
//     let params = {}
//
//     if (headers != null) {
//         let realHeader = {}
//         headers.forEach(item => {
//             realHeader[item.name] = item.value
//         })
//         params['headers'] = realHeader
//     }
//
//     if (body != null) {
//         let realBody = ""
//         body.forEach(item => {
//             realBody += `${item.name}=${item.value}&`
//         })
//         params['body'] = realBody
//     }
//
//     let res = request(method, url, params);
//     return res.getBody('utf8')
// }

function property(name, value) {
    let object = {}
    object.name = name
    object.value = value
    return object
}

function parseBook(node) {
    try {
        let $ = cheerio.load(node)
        let ret = {}
        ret.name = $('.bookname').text()
        ret.author = $('.author').text()
        ret.coverUrl = $('.bookimg img').attr("src").toString()
        ret.wordsNumber = $('.bookname').nextAll()[1].children[5].data
        ret.categoryDesc = $('.bookname').nextAll()[1].children[3].childNodes[0].data
        ret.desc = $('.intro').text()
        ret.newsSection = $('.chapter').text()
        return ret
    } catch (e) {
        return null
    }

}

function parseList(keyword) {
    try {
        log(`开始下载--------`)
        let header = [
            property("content-type", "application/x-www-form-urlencoded")
        ]
        let body = [
            property("searchkey", keyword)
        ]
        let html = fetch("https://so.9txs.org/www", header, body)
        log(`下载完成:${html.length}--------`)

        let $ = cheerio.load(html)
        let list = $('.library li')

        if (list == null || list.length === 0) {
            return []
        }

        log("搜索到的列表:" + list.length)

        let result = []
        list.map((i, node) => {
            result.push(parseBook(node))
        })

        log("搜索到的书籍:" + result.length)

        return result
    } catch (e) {
        log(`下载出错:${e.name},${e.stack}--------`)
    }
}


export function search(keyword) {
    try {
        let retJson = parseList(keyword)
        log(`搜索完成:${retJson.length}--------`)

        let ret = JSON.stringify(retJson, null, "    ")
        log(ret)
        return ret
    } catch (e) {
        log(`json 转字符异常${e.name},${e.stack}`)
    }
}

// search("斗破苍穹")
