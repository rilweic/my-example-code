package com.lichao666.okhttp;

import okhttp3.*;

import java.io.IOException;

public class OkhttpTest {
    public static void main(String[] args) throws IOException {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\"question\":\"全智贤\",\"bingResults\":{\n        \"_type\": \"SearchResponse\",\n        \"queryContext\": {\n            \"originalQuery\": \"全智贤\"\n        },\n        \"webPages\": {\n            \"webSearchUrl\": \"https://www.bing.com/search?q=%E5%85%A8%E6%99%BA%E8%B4%A4\",\n            \"totalEstimatedMatches\": 872000,\n            \"value\": [\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.0\",\n                    \"name\": \"全智贤（韩国女演员）_百度百科\",\n                    \"url\": \"https://baike.baidu.com/item/%E5%85%A8%E6%99%BA%E8%B4%A4/179392\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://baike.baidu.com/item/全智贤\",\n                    \"snippet\": \"全智贤（전지현，Gianna Jun），本名王智贤，1981年10月30日出生于韩国首尔江南区，毕业于东国大学话剧影像学系，韩国影视女演员、模特。1997年，作为封面模特出道。1998年，主演爱情剧《我心荡漾》进入演艺圈。1999年2月，主演的爱情片《白色情人节》上映，凭借该片获得了第35届韩国百想艺术大赏 ...\",\n                    \"dateLastCrawled\": \"2023-02-23T23:37:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.1\",\n                    \"name\": \"全智賢 - 维基百科，自由的百科全书\",\n                    \"url\": \"https://zh.wikipedia.org/wiki/%E5%85%A8%E6%99%BA%E8%B3%A2\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://zh.wikipedia.org/wiki/全智賢\",\n                    \"snippet\": \"全智贤的婚礼谢绝花束赠品，所收礼金将全部捐赠给新郎的外婆任职的慈善基金。 2013年4月24日 在首尔光华门广场，参加由韩国某食品品牌和首尔社会福利协会共同组织的社会公益活动。\",\n                    \"dateLastCrawled\": \"2023-02-23T15:15:00.0000000Z\",\n                    \"language\": \"zh_cht\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.2\",\n                    \"name\": \"全智贤 Gianna Jun的全部作品（23）\",\n                    \"url\": \"https://movie.douban.com/celebrity/1035649/movies?sortby=time\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://movie.douban.com/celebrity/1035649/movies?sortby=time\",\n                    \"snippet\": \"全智贤 Gianna Jun / 李政宰 Jung-Jae Lee / 河正宇 Jung-woo Ha / 吴达洙 Da... 8.1 / 217822人评价 来自星星的你 (2013) [ 演员 (饰 千颂伊) ]\",\n                    \"dateLastCrawled\": \"2023-02-23T01:56:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.3\",\n                    \"name\": \"全智贤 Gianna Jun\",\n                    \"url\": \"https://movie.douban.com/celebrity/1035649/\",\n                    \"thumbnailUrl\": \"https://www.bing.com/th?id=OIP.1ADs_FBV9nTGR_8lHBn-gQAAAA&w=80&h=80&c=1&pid=5.1\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://movie.douban.com/celebrity/1035649\",\n                    \"snippet\": \"全智贤，韩国著名影视女演员。其凭借《我的野蛮女友》《来自星星的你》两次风靡海内外，主演电影《盗贼同盟》《暗杀》均取得超千万观影人次票房，曾获韩国电影大钟奖最佳女主角奖、韩国百想艺术大赏电视部门最高大赏、韩国大众文化艺术奖总统表彰等荣誉。\",\n                    \"dateLastCrawled\": \"2023-02-22T08:40:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.4\",\n                    \"name\": \"全智贤向孙兴慜表白是粉丝，全智贤婆婆对儿媳与孙兴慜合影有反应|韩国|英国足球|巴西足球|足球竞赛_网易订阅\",\n                    \"url\": \"https://www.163.com/dy/article/HU4ATRQR0517U8SU.html\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://www.163.com/dy/article/HU4ATRQR0517U8SU.html\",\n                    \"snippet\": \"全智贤的婆婆先前以孙兴慜的铁杆粉丝而闻名，每当有关于孙兴慜令人印象深刻的英超联赛新闻时，她都会在自己的Instagram上提及他，以此展现自己孙兴慜粉丝的身份。当然她也很呵护儿媳全智贤的，当年有全智贤崔俊赫疑似离婚的传闻，此后澄清是谣言。\",\n                    \"dateLastCrawled\": \"2023-02-23T19:56:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.5\",\n                    \"name\": \"41岁全智贤素颜零修图美得惊艳！网友：又恋爱了|妆容|模特|千颂伊|女明星|野蛮女友_网易订阅\",\n                    \"url\": \"https://www.163.com/dy/article/HU6R3EN00552R0ST.html\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://www.163.com/dy/article/HU6R3EN00552R0ST.html\",\n                    \"snippet\": \"全智贤虽久未有影视作品，但广告代言不断，曝光率有增无减。 去年全智贤接下Burberry时尚品牌大使一职后，经常出镜拍画报，参加时装周。 绝美容颜和自体发光的肤况，让人惊叹她与《野蛮女友》和千颂伊年代几乎无丝毫差别！\",\n                    \"dateLastCrawled\": \"2023-02-23T20:01:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.6\",\n                    \"name\": \"全智贤 - 搜狗百科\",\n                    \"url\": \"https://baike.sogou.com/v93339.htm\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://baike.sogou.com/v93339.htm\",\n                    \"snippet\": \"全智贤（전지현，Gianna Jun，1981年10月30日-），本名王智贤，1981年10月30日出生于韩国首尔江南区，毕业于东国大学，韩国影视演员、模特。 1997年，从事模特行业，并出现在一系列电视作品中；1999年，出演电视剧《欢乐时光》，凭借该剧获得SBS演技大赏最佳新人奖；2001年，凭电影《我的野蛮女友 ...\",\n                    \"dateLastCrawled\": \"2023-02-23T10:33:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.7\",\n                    \"name\": \"T台秀｜全智贤带货风衣，李大牛是懂Burberry的_凤凰网\",\n                    \"url\": \"https://fashion.ifeng.com/c/8NaklFfJ8Ep\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://fashion.ifeng.com/c/8NaklFfJ8Ep\",\n                    \"snippet\": \"从首尔出发伦敦，全智贤的松弛感让看腻了精修图的网友拍手叫好：姐姐明明是去时装周，怎么直接在机场拍海报了？ 秀场头排，姐姐把风衣绑带 ...\",\n                    \"dateLastCrawled\": \"2023-02-22T22:55:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.8\",\n                    \"name\": \"宋慧乔米兰时装周扮相甜美，气质乖巧可爱，全智贤知性俏皮\",\n                    \"url\": \"https://www.aboluowang.com/2023/0223/1870110.html\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://www.aboluowang.com/2023/0223/1870110.html\",\n                    \"snippet\": \"全智贤在很多普通人的眼里，其实她的个性与气质更超于宋慧乔。 但在媒体人的眼中，乔妹的驾驭能力更强，大家都普遍认为乔妹扮相甜美，气质乖巧可爱。 而全智贤有时候走的是一种青春活力路线，在表情管理上面全智贤更自然一些。\",\n                    \"dateLastCrawled\": \"2023-02-24T08:35:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.9\",\n                    \"name\": \"全智贤 - Wikiwand\",\n                    \"url\": \"https://www.wikiwand.com/zh-cn/%E5%85%A8%E6%99%BA%E8%B3%A2\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://www.wikiwand.com/zh-cn/全智賢\",\n                    \"snippet\": \"全智贤（朝鲜语：전지현 Jun Ji Hyun，1981年10月30日－），本名：王智贤，韩国著名影视女演员、模特儿。15岁以时装杂志模特儿出道，2001年以韩国电影《我的野蛮女友》一跃成为韩国一线女星。2006年凭电影《爱无间》成为韩国身价最高的女演员。2014年，以SBS电视剧《来自星星的你》攀上演艺事业巅峰。\",\n                    \"dateLastCrawled\": \"2023-02-19T22:39:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.10\",\n                    \"name\": \"全智贤近况曝光，和富豪老公浪漫约会，儿子入读贵族学校\",\n                    \"url\": \"https://new.qq.com/rain/a/20211002A08PSH00\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://new.qq.com/rain/a/20211002A08PSH00\",\n                    \"snippet\": \"全智贤. 今年，全智贤已拍了16支广告，是当之无愧的广告女王。 不仅如此，全智贤还主演了《王国外传》和《智异山》两部作品。 值得一提的是《智异山》被某国际ott平台以1.5亿元的高价购入独家海外播出版权，让大家又一次见证了全智贤韩流女神的威力。 全 ...\",\n                    \"dateLastCrawled\": \"2023-02-23T19:56:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.11\",\n                    \"name\": \"41岁宋慧乔成韩国美貌之光！换新发型闪耀时尚周，状态比全智贤好_显高_效果_时装周\",\n                    \"url\": \"https://www.sohu.com/a/645352562_100271920\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://www.sohu.com/a/645352562_100271920\",\n                    \"snippet\": \"宋慧乔跟全智贤是两种截然不同的美女，全智贤的长相更加的大气，而且身材高挑，天生就拥有着超时尚的气质。. 相比较之下，宋慧乔的长相更加的清新邻家，身材也略显娇小，但是随着年龄的增长，宋慧乔的气场越来越强，今年41岁的她，跟年轻时候比起来更 ...\",\n                    \"dateLastCrawled\": \"2023-02-24T01:38:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.12\",\n                    \"name\": \"【全智贤】全演员18个角色名场面盘点，真就是十九岁火遍亚洲！_哔哩哔哩_bilibili\",\n                    \"url\": \"https://www.bilibili.com/video/BV1kb4y1b7Hb/\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://www.bilibili.com/video/BV1kb4y1b7Hb\",\n                    \"snippet\": \"，全智贤美貌大赏！ 【全智贤100个心动瞬间】，全智贤从1到36岁美丽瞬间，漂 亮 就 行，全智贤颜值变化史，【全智贤】一个从16岁美到40岁的女神，出演的电影混剪，有的人19岁就光芒四射，红遍亚洲了！\",\n                    \"dateLastCrawled\": \"2023-02-19T08:06:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.13\",\n                    \"name\": \"如何看待全智贤的气质？ - 知乎\",\n                    \"url\": \"https://www.zhihu.com/question/310487504\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://www.zhihu.com/question/310487504\",\n                    \"snippet\": \"而全智贤的美更是全方位的，不是单独以某个完美五官、某种身材特征取胜，而是五官、身材与仪互相配合，融合出一种既利落又纯媚的独特气质。 其实，全智贤的美也能给我们一些启发，流水线的整容永远都在迎合时代审美，而 迷失自我 的结果就是很多女 ...\",\n                    \"dateLastCrawled\": \"2023-02-23T14:28:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.14\",\n                    \"name\": \"《雏菊》电影在线观看_2006年韩国电影-韩剧网-韩剧DVD\",\n                    \"url\": \"https://www.hanritai.com/1561/\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://www.hanritai.com/1561\",\n                    \"snippet\": \"雏菊. 分类： 电影 地区： 韩国 年份： 2006 又名：爱无间 / 黛西 / Daisy. 看点： 剧情,爱情,全智贤,雏菊电影,郑宇成,文艺 主演： 全智贤,郑雨盛,李成宰,姜大卫,林迪安 导演： 刘伟强 状态： HD 更新： 2022-01-01 简介： 阿姆斯特丹，两个男人爱上了同一个女人，注定了一段纠缠的故事。\",\n                    \"dateLastCrawled\": \"2023-02-23T22:05:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.15\",\n                    \"name\": \"全智贤：被金刚狼夺走初吻，下嫁百亿老公，却被老板“窃听”十年\",\n                    \"url\": \"https://new.qq.com/rain/a/20211207A080W600\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://new.qq.com/rain/a/20211207A080W600\",\n                    \"snippet\": \"后来被全智贤发现后立马选择打电话报警，但是警方却因为没有证据未能给郑勋拓定罪，原来女神还有过这样凄惨的经历。 无奈之下，全智贤与郑勋拓解除了13年的压榨合同，恢复自由身的全智贤只好拒绝其他公司抛来的橄榄枝，干脆自己开工作室当了老板。\",\n                    \"dateLastCrawled\": \"2023-02-23T07:22:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.16\",\n                    \"name\": \"全智贤婚后这状态也太吓人了吧…… - 知乎\",\n                    \"url\": \"https://zhuanlan.zhihu.com/p/393898826\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://zhuanlan.zhihu.com/p/393898826\",\n                    \"snippet\": \"全智贤的好看，其实不是高度的惊艳感，而是像清酒一样刚入口的时候清清淡淡，但是后劲儿却逐渐增强的类型，很上头。 30岁之后，她脸上的婴儿肥由于胶原蛋白的减少而变薄，骨相也变得更加明显。\",\n                    \"dateLastCrawled\": \"2023-02-22T17:02:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.17\",\n                    \"name\": \"全智贤脸型五官分析：大饼脸、下巴后缩、单眼皮的第一美人我第一次见 - 知乎\",\n                    \"url\": \"https://zhuanlan.zhihu.com/p/25137899\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://zhuanlan.zhihu.com/p/25137899\",\n                    \"snippet\": \"换句话说，全智贤的脸面积其实是不大的，可是在镜头下，李冰冰给人感觉脸只有实际面积的百分之八十，全智贤则是百分之一百二（多的二十是下巴导致的）。. 这也是她有时候给人扁平感的原因。. 这里Miss Young想为她伸个冤。. 毕竟她是模特出身，174cm，骨架 ...\",\n                    \"dateLastCrawled\": \"2023-02-23T21:21:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.18\",\n                    \"name\": \"孙兴慜全智贤同框！看不出悬殊12岁 | 文学城\",\n                    \"url\": \"https://m.wenxuecity.com/news/2023/02/21/ent-239373.html\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://m.wenxuecity.com/news/2023/02/21/ent-239373.html\",\n                    \"snippet\": \"众所周知，全智贤目前已经 42 岁了，而孙兴慜才只有 30 岁，两人悬殊了一轮，但是同框画面确实如此和谐，\\\" 全女神 \\\" 的养颜功夫太强了，这个岁数还能光鲜艳丽，气质优雅，皮肤吹弹可破，颜值依然天花板，让人羡慕不已。\",\n                    \"dateLastCrawled\": \"2023-02-24T02:13:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.19\",\n                    \"name\": \"全智贤超话—新浪微博超话社区\",\n                    \"url\": \"https://weibo.com/p/10080832b44e183d15759cd2d8ce512f52ab4d?k=%E5%85%A8%E6%99%BA%E8%B4%A4&forward_name=pageinfo&_from_=topic_forward\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://weibo.com/p/10080832b44e183d15759cd2d8ce512f52ab4d?k=全智贤&forward_name...\",\n                    \"snippet\": \"导语： 全智贤（Gianna Jun，전지현 ），1981年10月30日生于首尔，韩国女演员。. 主演电影：《触不到的恋人》《我的野蛮女友》《雏菊》《盗贼同盟》《柏林》《暗杀》等，电视剧：《来自星星的你》《蓝色大海的传说》. 置顶 【澄清】关于全智贤本人及父母的国 ...\",\n                    \"dateLastCrawled\": \"2023-02-19T08:57:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                },\n                {\n                    \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.20\",\n                    \"name\": \"盗贼同盟_百度百科\",\n                    \"url\": \"https://baike.baidu.com/item/%E7%9B%97%E8%B4%BC%E5%90%8C%E7%9B%9F/3917365\",\n                    \"isFamilyFriendly\": true,\n                    \"displayUrl\": \"https://baike.baidu.com/item/盗贼同盟\",\n                    \"snippet\": \"《盗贼同盟》是由崔东勋执导，金允石、金惠秀、李政宰、全智贤等联合出演的一部犯罪动作片。讲述为窃走藏在澳门赌场内的稀世名钻，来自韩国和中国的十人盗窃团队展开联合行动的故事。该电影上映时连续刷新韩国影史十七项票房纪录，总票房达1298万3341观影人次，位列韩国影史票房第一（后 ...\",\n                    \"dateLastCrawled\": \"2023-02-23T03:05:00.0000000Z\",\n                    \"language\": \"zh_chs\",\n                    \"isNavigational\": false\n                }\n            ]\n        },\n        \"rankingResponse\": {\n            \"mainline\": {\n                \"items\": [\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 0,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.0\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 1,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.1\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 2,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.2\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 3,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.3\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 4,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.4\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 5,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.5\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 6,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.6\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 7,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.7\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 8,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.8\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 9,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.9\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 10,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.10\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 11,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.11\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 12,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.12\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 13,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.13\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 14,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.14\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 15,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.15\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 16,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.16\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 17,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.17\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 18,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.18\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 19,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.19\"\n                        }\n                    },\n                    {\n                        \"answerType\": \"WebPages\",\n                        \"resultIndex\": 20,\n                        \"value\": {\n                            \"id\": \"https://api.bing.microsoft.com/api/v7/#WebPages.20\"\n                        }\n                    }\n                ]\n            }\n        }\n    }}");
//        Request request = new Request.Builder()
//                .url("https://phind.com/api/tldr")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/json")
//                .build();
//        Response response = client.newCall(request).execute();
//        System.out.println(response.body().toString());
//        OkHttpUtil.po
    }
}
