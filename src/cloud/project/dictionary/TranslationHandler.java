package cloud.project.dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class TranslationHandler {
	private final static String simpleChar = "肮囘丢并干夫布占畲来局俣系幸咱杰备效家雇当尽罗攒凶兑内册幂涂渎处别剥铲札胜绩匀汇奁椟恤咤吴唣念问衔岩尝恶苏囱国垧埯采执阶尧报碱茔尘砖硗寿梦姗娱袅嬷懒尴届嵛襕荫廪强彦恒耻悦闷爱栗虑懔蒙欢戬户扦舍构摇撑败驱毙晋畅胧栅荣盘规檩苘钦殁毁绒牦泛污没莅渌渊灭荥颍潜舄阔灾辉茕荧颎营闸荦获珏瑶莹罂迭荡视眦硅碜秘禄秃籼税谷颖颓节范蓑藤钥纟纠纪纣约红纡纥纨纫纹纳纽纾纯纰纼纱纮纸级纷纭纴纺细绂绁绅纻绍绀绋绐绌终组绊绗结绝绦绔绞络绚给绖统绛绢绑绡绠绨绣绤绥经综缍绿绸绻线绶维绹绾纲网绷缀纶绺绮绽绰绫绵绲缁绯缗绪绬绱缃缄缂缉缎缔缘缌编缓缅纬缑缈练缏缇致缊萦缙缢缒绉缣缞缚缜缟缛缝缡缩纵缧纤缦缕缥缫缪襁缯织缮缭绕缋绳绘茧缰缳缲缴绎继缤缱颣缬纩续缠缨缵缆钵骂芈膻锄闻脉脱铺馆艹蜕里见觃觅觇觋觍觎觊觏觑觐觉览觌观讠订讣计讯讧讨讦讱训讪讫记讹讶讼诀讷谌讻访设许诉诃诊证诂诋讵诈诒诏评诐诇诎诅词诩询诣试诗诧诟诡诠诘话该详诜诙诖诔诛诓认诳诶诞诱诮语诚诫诬误诰诵诲说谁课谇诽谊调谄谆谈诿请诤诹诼谅论谂谀谍谞谝谥诨谔谛谐谏谕讳谙讽诸谚谖诺谋谒谓诌谎谜谧谑谡谤谦讲谢谣谟谪谬讴谨谩谲讥谮识谯谭谱谵译议谴诪谫读雠谗让谰谶赞谠谳贝贞贠负财贡贫货贩贪贯责贮贳赀贰贵贬贷贶费贴贻贸贺贲赂赁贿赅资贾贼赃赈赊赇赒赉赐赏赔赓贤贱赋赕质赍账赌赆赖赗赚赙购赛赜贽赘赟赠赝赡赢赑赎赣迹车轧轨轪轩轫轭软轷轸轱轴轵轺轲轶轼较辂辁辀载轾辄辅轻辆辎辋辍辊辇辈轮辌辑辏输辐辒辗辖辕辘转辙轿辚轰辔轹轳迤遥钆钇钌钊钉钋针钓钐钏钒钗钍钕钎钯钫钘钭钚钠钝钩钤钣钑钞钮钧钟钙钬钛钪铌铈钶铃钴钹铍钰钸铀钿钾铁钻铊铉铋铂钷钳铆铅钺钲钼钽锎铏铰铒铬铪银铳铜铚铣铨铢铭铫铦铑铷铱铟铵铥铕铯铐铞锐销锈锑锉铝镅锒锌钡铤铗锋铻镯锊锓铘锃锔锇铓铖锆锂铽锍锯钢锞锖锫锩铔锥锕锟锤锱铮锛锬锭锜钱锦锚锠锡锢错锰铼镎锝锨锪钔锴锳锅镀锷铡钖锻锽锸锲锘锹锾键锶锗镁锿镑镕锁镉镈镃钨镏铠铩锼镐镇镒镋镍镓镌镞链镆镙镠镝铿锵镗镘镛镜镖镂镚铧镤镪铙铴镣铹镦镡镫镢镨锏镄镰镭镮铎铛镱铸镬镔镲锧镴铄镳镥镧镵镶镊镩锣长门闩闪闫闬闭闶闳闰闲间闵闹阂阁阀闺闽阃阆闾阅阊阉阎阏阍阈阌阒闱阕阑阇阗阘闿阖阙闯阚阓阐阛闼鸡韧鼗韦韨韩韪韬韫页顶顷项顺顸须顼颂颀颃预顽颁顿颇领颌颉颐颏颒颊颋颕颔颈频颗题额颚颜颙颛颡颠颟颢顾颤颥颦颅颞颧风飐飑飒飓飔飖飕飗飘飙飞饥饤饦饨饪饫饬饭饮饴饲饱饰饳饺饸饼饷饵饹饻饽馁饿馂饾馄馃饯馅饧馉馇馎饩馈馏馊馌馍馒馐馑馓馔饶馋馕马驭驮驰驯驲驳驻驽驹驵驾骀驸驶驼驷骈骇骃骆骎骏骋骍骓骔骒骑骐验骛骗鬃骙骞骘骝驺骚骟骡骜骖骠骢骅骕骁骣骄驿骤驴骧骥骦骊骉鲠鬓鱼鱽鱾鲀鲁鲂鱿鲄鲅鲆鲌鲉鲧鲏鲇鲐鲍鲋鲊鲒鲘鲕鲖鲔鲛鲑鲜鲓鲪鳀鲝鲩鲤鲨鲬鲻鲯鲭鲞鲷鲴鲱鲵鲲鲳鲸鲮鲰鲶鲺鲹鲫鳊鳈鲗鳂鲽鳇鳅鲾鳄鳆鳃鳁鳒鳑鳋鲥鳏鳎鳐鳍鲢鳌鳓鳘鲦鲣鳗鳛鳔鳉鳙鳕鳖鳟鳝鳜鳞鲟鲼鲎鲙鳣鳡鳢鲿鲚鳠鲈鲡鸟鸠鸤鸣鸢鸩鸨鸦鸰鸵鸳鸲鸮鸱鸪鸯鸭鸴鸸鸹鸻鸿鸽鸺鸼鹀鹃鹆鹁鹈鹅鹄鹉鹌鹏鹐鹎鹊鹓鹍鸫鹑鹒鹋鹙鹕鹗鹖鹛鹜鸧鹟鹤鹠鹡鹘鹣鹚鹢鹞鹝鹧鹥鸥鸷鹨鸶鹪鹔鹩鹫鹇鹬鹰鹭鹯鹱鹲鸬鹴鹦鹳鹂鸾鹾麦麸黄黾鼋鼌鼍鼹齐齑齿龀龁龂龅龇龃龆龄龈龊龉龋龌龙龚龛龟万与丑专业丛东丝两严丧个丬丰临为丽举么义乌乐乔习乡书买乱争于亏云亘亚产亩亲亵亸亿仅从仑仓仪们价众优伙会伛伞伟传伤伥伦伧伪伫体余佣佥侠侣侥侦侧侨侩侪侬俦俨俩俪俭债倾偬偻偾偿傥傧储傩儿兖党兰关兴兹养兽冁冈写军农冢冯冲决况冻净凄凉凌减凑凛几凤凫凭凯击凼凿刍划刘则刚创删刬刭刽刿剀剂剐剑剧劝办务劢动励劲劳势勋勐勚匦匮区医华协单卖卢卤卧卫却卺厂厅历厉压厌厍厕厢厣厦厨厩厮县参叆叇双发变叙叶号叹叽吁后吓吕吗吣吨听启呒呓呕呖呗员呙呛呜咏咔咙咛咝咴咸哌响哑哒哓哔哕哗哙哜哝哟唛唝唠唡唢唤唿啧啬啭啮啰啴啸喷喽喾嗫呵嗳嘘嘤嘱噜噼嚣团园围囵图圆圣圹场坂坏块坚坛坜坝坞坟坠垄垆垒垦垩垫垭垯垱垲垴埘埙埚堑堕塆墙壮声壳壶壸复够头夸夹夺奂奋奖奥妆妇妈妩妪妫姜娄娅娆娇娈娲娴婳婴婵婶媪嫒嫔嫱孙学孪宁宝实宠审宪宫宽宾寝对寻导将尔尸层屃屉属屡屦屿岁岂岖岗岘岙岚岛岭岳岽岿峃峄峡峣峤峥峦崂崃崄崭嵘嵚嵝嵴巅巩巯币帅师帏帐帘帜带帧帮帱帻帼幞广庄庆庐庑库应庙庞废庼开异弃张弥弪弯弹归录彟彻径徕御忆忏忧忾怀态怂怃怄怅怆怜总怼怿恋恳恸恹恺恻恼恽悫悬悭悯惊惧惨惩惫惬惭惮惯愍愠愤愦愿慑慭憷懑戆戋戏戗战扎扑扩扪扫扬扰抚抛抟抠抡抢护担拟拢拣拥拦拧拨择挂挚挛挜挝挞挟挠挡挢挣挤挥挦捞损捡换捣据捻掳掴掷掸掺掼揸揽揿搀搁搂搅携摄摅摆摈摊撄撵撷撸撺擞敌敛数斋斓斗斩断无旧时旷旸昙昼昽显晒晓晔晕晖暂暧术朴机杀杂权条杨杩极枞枢枣枥枧枨枪枫枭柜柠柽栀标栈栉栊栋栌栎栏树栖样栾桊桠桡桢档桤桥桦桧桨桩梼梾检棂椁椠椤椭楼榄榇榈榉槚槛槟槠横樯樱橥橱橹橼檐欤欧歼殇残殒殓殚殡殴毂毕毡毵氇气氢氩氲汉汤汹沓沟沣沤沥沦沧沨沩沪沵泞泪泶泷泸泺泻泼泽泾洁洒洼浃浅浆浇浈浉浊测浍济浏浐浑浒浓浔浕涌涛涝涞涟涠涡涢涣涤润涧涨涩淀渍渐渑渔渖渗温游湾湿溃溅溆溇滗滚滞滟滠满滢滤滥滦滨滩滪潆潇潋潍潴澜濑濒灏灯灵灿炀炉炖炜炝点炼炽烁烂烃烛烟烦烧烨烩烫烬热焕焖焘煅煳熘爷牍牵牺犊状犷犸犹狈狍狝狞独狭狮狯狰狱狲猃猎猕猡猪猫猬献獭玑玙玚玛玮环现玱玺珉珐珑珰珲琎琏琐琼瑷璇璎瓒瓮瓯电画畴疖疗疟疠疡疬疮疯疱疴痈痉痒痖痨痪痫痴瘅瘆瘗瘘瘪瘫瘾瘿癞癣癫癯皑皱皲盏盐监盖盗眍眬着睁睐睑瞒瞩矫矶矾矿砀码砗砚砜砺砻砾础硁硕硖硙硚确硷碍碛碹礼祎祢祯祷祸禀禅离秆种积称秽秾稆稣稳穑穷窃窍窑窜窝窥窦窭竖竞笃笋笔笕笼笾筑筚筛筜筝筹签简箓箦箧箨箩箪箫篑篓篮篱簖籁籴类粜粝粤粪粮糁糇紧絷缐罚罢罴羁羟羡翘翙翚耢耧耸聂聋职聍联聩聪肃肠肤肷肾肿胀胁胆胨胪胫胶脍脏脐脑脓脔脚脶脸腊腌腘腭腻腼腽腾膑臜舆舣舰舱舻艰艳艺芗芜芦苁苇苈苋苌苍苎苹茎茏茑荆荐荙荚荛荜荞荟荠荤荨荩荪荬荭荮药莜莱莲莳莴莶莸莺莼萚萝萤萧萨葱蒇蒉蒋蒌蓝蓟蓠蓣蓥蓦蔷蔹蔺蔼蕲蕴薮藁藓虏虚虫虬虮虽虾虿蚀蚁蚂蚕蚝蚬蛊蛎蛏蛮蛰蛱蛲蛳蛴蜗蜡蝇蝈蝉蝎蝼蝾螀螨蟏衅补衬衮袄袆袜袭袯装裆裈裢裣裤裥褛褴觞触觯詟誉誊谘豮赪赵赶趋趱趸跃跄跖跞践跶跷跸跹跻踊踌踪踬踯蹑蹒蹰蹿躏躜躯辞辩辫边辽达迁过迈运还这进远违连迟迩迳适选逊递逦逻遗邓邝邬邮邹邺邻郁郄郏郐郑郓郦郧郸酝酦酱酽酾酿释巨鉴銮錾锺阄阋队阳阴阵际陆陇陈陉陕陧陨险随隐隶隽难雏雳雾霁霉霭靓静靥鞑鞒鞯鞴韵飏飨餍髅髋髌魇魉黉黡黩黪齄志制只松面准拼";
	// private final static String longedChar = "回丟並乾伕佈佔佘來侷俁係倖偺傑備傚傢僱儅儘儸儹兇兌內冊冪凃凟處別剝剷劄勝績勻匯奩匵卹吒吳唕念問啣岩嘗噁蘇囪國坰垵埰執堦堯報堿塋塵磚墝壽夢姍娛嫋嬤懶尷屆崳幱蔭廩強彥恆恥悅悶愛慄慮懍懞歡戩戶扡捨構搖撐敗驅斃晉暢朧柵榮盤規檁檾欽歿毀絨氂氾汙沒蒞淥淵滅滎潁潛潟闊災煇煢熒熲營閘犖獲玨瑤瑩罌疊盪視眥矽硶祕祿禿秈稅穀穎頹節範簑籐籥糸糾紀紂約紅紆紇紈紉紋納紐紓純紕紖紗紘紙級紛紜紝紡細紱紲紳紵紹紺紼紿絀終組絆絎結絕絛絝絞絡絢給絰統絳絹綁綃綆綈綉綌綏經綜綞綠綢綣綫綬維綯綰綱網綳綴綸綹綺綻綽綾綿緄緇緋緍緒緓緔緗緘緙緝緞締緣緦編緩緬緯緱緲練緶緹緻緼縈縉縊縋縐縑縗縛縝縞縟縫縭縮縱縲縴縵縷縹繅繆繈繒織繕繚繞繢繩繪繭繮繯繰繳繹繼繽繾纇纈纊續纏纓纘纜缽罵羋羶鋤聞脈脫舖館艸蛻裡見覎覓覘覡覥覦覬覯覷覲覺覽覿觀訁訂訃計訊訌討訐訒訓訕訖記訛訝訟訣訥諶訩訪設許訴訶診證詁詆詎詐詒詔評詖詗詘詛詞詡詢詣試詩詫詬詭詮詰話該詳詵詼詿誄誅誆認誑誒誕誘誚語誠誡誣誤誥誦誨說誰課誶誹誼調諂諄談諉請諍諏諑諒論諗諛諜諝諞諡諢諤諦諧諫諭諱諳諷諸諺諼諾謀謁謂謅謊謎謐謔謖謗謙講謝謠謨謫謬謳謹謾譎譏譖識譙譚譜譫譯議譴譸譾讀讎讒讓讕讖讚讜讞貝貞貟負財貢貧貨販貪貫責貯貰貲貳貴貶貸貺費貼貽貿賀賁賂賃賄賅資賈賊贓賑賒賕賙賚賜賞賠賡賢賤賦賧質賫賬賭賮賴賵賺賻購賽賾贄贅贇贈贋贍贏贔贖贛跡車軋軌軑軒軔軛軟軤軫軲軸軹軺軻軼軾較輅輇輈載輊輒輔輕輛輜輞輟輥輦輩輪輬輯輳輸輻輼輾轄轅轆轉轍轎轔轟轡轢轤迆遙釓釔釕釗釘釙針釣釤釧釩釵釷釹釺鈀鈁鈃鈄鈈鈉鈍鈎鈐鈑鈒鈔鈕鈞鐘鈣鈥鈦鈧鈮鈰鈳鈴鈷鈸鈹鈺鈽鈾鈿鉀鐵鉆鉈鉉鉍鉑鉕鉗鉚鉛鉞鉦鉬鉭鉲鉶鉸鉺鉻鉿銀銃銅銍銑銓銖銘銚銛銠銣銥銦銨銩銪銫銬銱銳銷鏽銻銼鋁鋂鋃鋅鋇鋌鋏鋒鋙鋜鋝鋟鋣鋥鋦鋨鋩鋮鋯鋰鋱鋶鋸鋼錁錆錇錈錏錐錒錕錘錙錚錛錟錠錡錢錦錨錩錫錮錯錳錸錼鍀鍁鍃鍆鍇鍈鍋鍍鍔鍘鍚鍛鍠鍤鍥鍩鍫鍰鍵鍶鍺鎂鎄鎊鎔鎖鎘鎛鎡鎢鎦鎧鎩鎪鎬鎮鎰鎲鎳鎵鎸鏃鏈鏌鏍鏐鏑鏗鏘鏜鏝鏞鏡鏢鏤鏰鏵鏷鏹鐃鐋鐐鐒鐓鐔鐙鐝鐠鐧鐨鐮鐳鐶鐸鐺鐿鑄鑊鑌鑔鑕鑞鑠鑣鑥鑭鑱鑲鑷鑹鑼長門閂閃閆閈閉閌閎閏閑間閔閙閡閣閥閨閩閫閬閭閱閶閹閻閼閽閾閿闃闈闋闌闍闐闒闓闔闕闖闞闠闡闤闥雞靭鞀韋韍韓韙韜韞頁頂頃項順頇須頊頌頎頏預頑頒頓頗領頜頡頤頦頮頰頲頴頷頸頻顆題額顎顏顒顓顙顛顢顥顧顫顬顰顱顳顴風颭颮颯颶颸颻颼飀飃飆飛飢飣飥飩飪飫飭飯飲飴飼飽飾飿餃餄餅餉餌餎餏餑餒餓餕餖餛餜餞餡餳餶餷餺餼餽餾餿饁饃饅饈饉饊饌饒饞饢馬馭馱馳馴馹駁駐駑駒駔駕駘駙駛駝駟駢駭駰駱駸駿騁騂騅騌騍騎騏騐騖騗騣騤騫騭騮騶騷騸騾驁驂驃驄驊驌驍驏驕驛驟驢驤驥驦驪驫骾髩魚魛魢魨魯魴魷魺鮁鮃鮊鮋鮌鮍鮎鮐鮑鮒鮓鮚鮜鮞鮦鮪鮫鮭鮮鮳鮶鮷鮺鯇鯉鯊鯒鯔鯕鯖鯗鯛鯝鯡鯢鯤鯧鯨鯪鯫鯰鯴鯵鯽鯿鰁鰂鰃鰈鰉鰌鰏鰐鰒鰓鰛鰜鰟鰠鰣鰥鰨鰩鰭鰱鰲鰳鰵鰷鰹鰻鰼鰾鱂鱅鱈鱉鱒鱓鱖鱗鱘鱝鱟鱠鱣鱤鱧鱨鱭鱯鱸鱺鳥鳩鳲鳴鳶鴆鴇鴉鴒鴕鴛鴝鴞鴟鴣鴦鴨鴬鴯鴰鴴鴻鴿鵂鵃鵐鵑鵒鵓鵜鵝鵠鵡鵪鵬鵮鵯鵲鵷鵾鶇鶉鶊鶓鶖鶘鶚鶡鶥鶩鶬鶲鶴鶹鶺鶻鶼鶿鷁鷂鷊鷓鷖鷗鷙鷚鷥鷦鷫鷯鷲鷳鷸鷹鷺鸇鸌鸏鸕鸘鸚鸛鸝鸞鹺麥麩黃黽黿鼂鼉鼴齊齏齒齔齕齗齙齜齟齠齡齦齪齬齲齷龍龔龕龜萬與醜專業叢東絲兩嚴喪個爿豐臨為麗舉麼義烏樂喬習鄉書買亂爭於虧雲亙亞產畝親褻嚲億僅從侖倉儀們價眾優夥會傴傘偉傳傷倀倫傖偽佇體餘傭僉俠侶僥偵側僑儈儕儂儔儼倆儷儉債傾傯僂僨償儻儐儲儺兒兗黨蘭關興茲養獸囅岡寫軍農塚馮衝決況凍淨淒涼淩減湊凜幾鳳鳧憑凱擊氹鑿芻劃劉則剛創刪剗剄劊劌剴劑剮劍劇勸辦務勱動勵勁勞勢勳猛勩匭匱區醫華協單賣盧鹵臥衛卻巹廠廳曆厲壓厭厙廁廂厴廈廚廄廝縣參靉靆雙發變敘葉號歎嘰籲後嚇呂嗎唚噸聽啟嘸囈嘔嚦唄員咼嗆嗚詠哢嚨嚀噝噅鹹呱響啞噠嘵嗶噦嘩噲嚌噥喲嘜嗊嘮啢嗩喚呼嘖嗇囀齧囉嘽嘯噴嘍嚳囁嗬噯噓嚶囑嚕劈囂團園圍圇圖圓聖壙場阪壞塊堅壇壢壩塢墳墜壟壚壘墾堊墊埡墶壋塏堖塒塤堝塹墮壪牆壯聲殼壺壼複夠頭誇夾奪奐奮獎奧妝婦媽嫵嫗媯薑婁婭嬈嬌孌媧嫻嫿嬰嬋嬸媼嬡嬪嬙孫學孿寧寶實寵審憲宮寬賓寢對尋導將爾屍層屭屜屬屢屨嶼歲豈嶇崗峴嶴嵐島嶺嶽崠巋嶨嶧峽嶢嶠崢巒嶗崍嶮嶄嶸嶔嶁脊巔鞏巰幣帥師幃帳簾幟帶幀幫幬幘幗襆廣莊慶廬廡庫應廟龐廢廎開異棄張彌弳彎彈歸錄彠徹徑徠禦憶懺憂愾懷態慫憮慪悵愴憐總懟懌戀懇慟懨愷惻惱惲愨懸慳憫驚懼慘懲憊愜慚憚慣湣慍憤憒願懾憖怵懣戇戔戲戧戰紮撲擴捫掃揚擾撫拋摶摳掄搶護擔擬攏揀擁攔擰撥擇掛摯攣掗撾撻挾撓擋撟掙擠揮撏撈損撿換搗據撚擄摑擲撣摻摜摣攬撳攙擱摟攪攜攝攄擺擯攤攖攆擷擼攛擻敵斂數齋斕鬥斬斷無舊時曠暘曇晝曨顯曬曉曄暈暉暫曖術樸機殺雜權條楊榪極樅樞棗櫪梘棖槍楓梟櫃檸檉梔標棧櫛櫳棟櫨櫟欄樹棲樣欒棬椏橈楨檔榿橋樺檜槳樁檮棶檢欞槨槧欏橢樓欖櫬櫚櫸檟檻檳櫧橫檣櫻櫫櫥櫓櫞簷歟歐殲殤殘殞殮殫殯毆轂畢氈毿氌氣氫氬氳漢湯洶遝溝灃漚瀝淪滄渢溈滬濔濘淚澩瀧瀘濼瀉潑澤涇潔灑窪浹淺漿澆湞溮濁測澮濟瀏滻渾滸濃潯濜湧濤澇淶漣潿渦溳渙滌潤澗漲澀澱漬漸澠漁瀋滲溫遊灣濕潰濺漵漊潷滾滯灩灄滿瀅濾濫灤濱灘澦瀠瀟瀲濰瀦瀾瀨瀕灝燈靈燦煬爐燉煒熗點煉熾爍爛烴燭煙煩燒燁燴燙燼熱煥燜燾煆糊溜爺牘牽犧犢狀獷獁猶狽麅獮獰獨狹獅獪猙獄猻獫獵獼玀豬貓蝟獻獺璣璵瑒瑪瑋環現瑲璽瑉琺瓏璫琿璡璉瑣瓊璦璿瓔瓚甕甌電畫疇癤療瘧癘瘍鬁瘡瘋皰屙癰痙癢瘂癆瘓癇癡癉瘮瘞瘺癟癱癮癭癩癬癲臒皚皺皸盞鹽監蓋盜瞘矓著睜睞瞼瞞矚矯磯礬礦碭碼硨硯碸礪礱礫礎硜碩硤磑礄確鹼礙磧镟禮禕禰禎禱禍稟禪離稈種積稱穢穠穭穌穩穡窮竊竅窯竄窩窺竇窶豎競篤筍筆筧籠籩築篳篩簹箏籌簽簡籙簀篋籜籮簞簫簣簍籃籬籪籟糴類糶糲粵糞糧糝餱緊縶線罰罷羆羈羥羨翹翽翬耮耬聳聶聾職聹聯聵聰肅腸膚膁腎腫脹脅膽腖臚脛膠膾髒臍腦膿臠腳腡臉臘醃膕齶膩靦膃騰臏臢輿艤艦艙艫艱豔藝薌蕪蘆蓯葦藶莧萇蒼苧蘋莖蘢蔦荊薦薘莢蕘蓽蕎薈薺葷蕁藎蓀蕒葒葤藥蓧萊蓮蒔萵薟蕕鶯蓴蘀蘿螢蕭薩蔥蕆蕢蔣蔞藍薊蘺蕷鎣驀薔蘞藺藹蘄蘊藪槁蘚虜虛蟲虯蟣雖蝦蠆蝕蟻螞蠶蠔蜆蠱蠣蟶蠻蟄蛺蟯螄蠐蝸蠟蠅蟈蟬蠍螻蠑螿蟎蠨釁補襯袞襖褘襪襲襏裝襠褌褳襝褲襇褸襤觴觸觶讋譽謄諮豶赬趙趕趨趲躉躍蹌蹠躒踐躂蹺蹕躚躋踴躊蹤躓躑躡蹣躕躥躪躦軀辭辯辮邊遼達遷過邁運還這進遠違連遲邇逕適選遜遞邐邏遺鄧鄺鄔郵鄒鄴鄰鬱郤郟鄶鄭鄆酈鄖鄲醞醱醬釅釃釀釋钜鑒鑾鏨鍾鬮鬩隊陽陰陣際陸隴陳陘陝隉隕險隨隱隸雋難雛靂霧霽黴靄靚靜靨韃鞽韉韝韻颺饗饜髏髖髕魘魎黌黶黷黲齇誌製隻鬆麵準拚";
	private final static String longedChar = "骯回丟並幹夫布占佘來局俁系幸咱傑備效家雇當盡羅攢凶兌內冊冪塗瀆處別剝鏟劄勝績勻匯奩櫝恤吒吳唕念問銜岩嘗惡蘇囪國坰垵采執階堯報堿塋塵磚磽壽夢姍娛嫋嬤懶尷屆崳襴蔭廩強彥恆恥悅悶愛栗慮懍蒙歡戩戶扡舍構搖撐敗驅斃晉暢朧柵榮盤規檁檾欽歿毀絨犛泛汙沒蒞淥淵滅滎潁潛舄闊災輝煢熒熲營閘犖獲玨瑤瑩罌迭蕩視眥矽磣秘祿禿秈稅穀穎頹節範蓑藤鑰糸糾紀紂約紅紆紇紈紉紋納紐紓純紕紖紗紘紙級紛紜紝紡細紱絏紳紵紹紺紼紿絀終組絆絎結絕絛絝絞絡絢給絰統絳絹綁綃綆綈繡綌綏經綜綞綠綢綣線綬維綯綰綱網繃綴綸綹綺綻綽綾綿緄緇緋緡緒緓緔緗緘緙緝緞締緣緦編緩緬緯緱緲練緶緹致縕縈縉縊縋縐縑縗縛縝縞縟縫縭縮縱縲纖縵縷縹繅繆繈繒織繕繚繞繢繩繪繭韁繯繰繳繹繼繽繾纇纈纊續纏纓纘纜缽罵羋膻鋤聞脈脫鋪館艸蛻裡見覎覓覘覡覥覦覬覯覷覲覺覽覿觀訁訂訃計訊訌討訐訒訓訕訖記訛訝訟訣訥諶訩訪設許訴訶診證詁詆詎詐詒詔評詖詗詘詛詞詡詢詣試詩詫詬詭詮詰話該詳詵詼詿誄誅誆認誑誒誕誘誚語誠誡誣誤誥誦誨說誰課誶誹誼調諂諄談諉請諍諏諑諒論諗諛諜諝諞諡諢諤諦諧諫諭諱諳諷諸諺諼諾謀謁謂謅謊謎謐謔謖謗謙講謝謠謨謫謬謳謹謾譎譏譖識譙譚譜譫譯議譴譸譾讀讎讒讓讕讖贊讜讞貝貞貟負財貢貧貨販貪貫責貯貰貲貳貴貶貸貺費貼貽貿賀賁賂賃賄賅資賈賊贓賑賒賕賙賚賜賞賠賡賢賤賦賧質齎賬賭贐賴賵賺賻購賽賾贄贅贇贈贗贍贏贔贖贛跡車軋軌軑軒軔軛軟軤軫軲軸軹軺軻軼軾較輅輇輈載輊輒輔輕輛輜輞輟輥輦輩輪輬輯輳輸輻轀輾轄轅轆轉轍轎轔轟轡轢轤迤遙釓釔釕釗釘釙針釣釤釧釩釵釷釹釺鈀鈁鈃鈄鈈鈉鈍鉤鈐鈑鈒鈔鈕鈞鐘鈣鈥鈦鈧鈮鈰鈳鈴鈷鈸鈹鈺鈽鈾鈿鉀鐵鑽鉈鉉鉍鉑鉕鉗鉚鉛鉞鉦鉬鉭鉲鉶鉸鉺鉻鉿銀銃銅銍銑銓銖銘銚銛銠銣銥銦銨銩銪銫銬銱銳銷鏽銻銼鋁鋂鋃鋅鋇鋌鋏鋒鋙鐲鋝鋟鋣鋥鋦鋨鋩鋮鋯鋰鋱鋶鋸鋼錁錆錇錈錏錐錒錕錘錙錚錛錟錠錡錢錦錨錩錫錮錯錳錸錼鍀鍁鍃鍆鍇鍈鍋鍍鍔鍘鍚鍛鍠鍤鍥鍩鍬鍰鍵鍶鍺鎂鎄鎊鎔鎖鎘鎛鎡鎢鎦鎧鎩鎪鎬鎮鎰钂鎳鎵鐫鏃鏈鏌鏍鏐鏑鏗鏘鏜鏝鏞鏡鏢鏤鏰鏵鏷鏹鐃鐋鐐鐒鐓鐔鐙钁鐠鐧鐨鐮鐳鐶鐸鐺鐿鑄鑊鑌鑔鑕鑞鑠鑣鑥鑭鑱鑲鑷鑹鑼長門閂閃閆閈閉閌閎閏閒間閔鬧閡閣閥閨閩閫閬閭閱閶閹閻閼閽閾閿闃闈闋闌闍闐闒闓闔闕闖闞闠闡闤闥雞韌鞀韋韍韓韙韜韞頁頂頃項順頇須頊頌頎頏預頑頒頓頗領頜頡頤頦頮頰頲頴頷頸頻顆題額顎顏顒顓顙顛顢顥顧顫顬顰顱顳顴風颭颮颯颶颸颻颼飀飄飆飛饑飣飥飩飪飫飭飯飲飴飼飽飾飿餃餄餅餉餌餎餏餑餒餓餕餖餛餜餞餡餳餶餷餺餼饋餾餿饁饃饅饈饉饊饌饒饞饢馬馭馱馳馴馹駁駐駑駒駔駕駘駙駛駝駟駢駭駰駱駸駿騁騂騅騌騍騎騏驗騖騙鬃騤騫騭騮騶騷騸騾驁驂驃驄驊驌驍驏驕驛驟驢驤驥驦驪驫鯁鬢魚魛魢魨魯魴魷魺鮁鮃鮊鮋鯀鮍鯰鮐鮑鮒鮓鮚鮜鮞鮦鮪鮫鮭鮮鮳鮶鯷鮺鯇鯉鯊鯒鯔鯕鯖鯗鯛鯝鯡鯢鯤鯧鯨鯪鯫鯰鯴鯵鯽鯿鰁鰂鰃鰈鰉鰍鰏鱷鰒鰓鰮鰜鰟鰠鰣鰥鰨鰩鰭鰱鼇鰳鰵鰷鰹鰻鰼鰾鱂鱅鱈鱉鱒鱔鱖鱗鱘鱝鱟鱠鱣鱤鱧鱨鱭鱯鱸鱺鳥鳩鳲鳴鳶鴆鴇鴉鴒鴕鴛鴝鴞鴟鴣鴦鴨鴬鴯鴰鴴鴻鴿鵂鵃鵐鵑鵒鵓鵜鵝鵠鵡鵪鵬鵮鵯鵲鵷鶤鶇鶉鶊鶓鶖鶘鶚鶡鶥鶩鶬鶲鶴鶹鶺鶻鶼鶿鷁鷂鷊鷓鷖鷗鷙鷚鷥鷦鷫鷯鷲鷳鷸鷹鷺鸇鸌鸏鸕鸘鸚鸛鸝鸞鹺麥麩黃黽黿鼂鼉鼴齊齏齒齔齕齗齙齜齟齠齡齦齪齬齲齷龍龔龕龜萬與醜專業叢東絲兩嚴喪個爿豐臨為麗舉麼義烏樂喬習鄉書買亂爭於虧雲亙亞產畝親褻嚲億僅從侖倉儀們價眾優夥會傴傘偉傳傷倀倫傖偽佇體余傭僉俠侶僥偵側僑儈儕儂儔儼倆儷儉債傾傯僂僨償儻儐儲儺兒兗党蘭關興茲養獸囅岡寫軍農塚馮沖決況凍淨淒涼淩減湊凜幾鳳鳧憑凱擊氹鑿芻劃劉則剛創刪剗剄劊劌剴劑剮劍劇勸辦務勱動勵勁勞勢勳猛勩匭匱區醫華協單賣盧鹵臥衛卻巹廠廳曆厲壓厭厙廁廂厴廈廚廄廝縣參靉靆雙發變敘葉號歎嘰籲後嚇呂嗎唚噸聽啟嘸囈嘔嚦唄員咼嗆嗚詠哢嚨嚀噝噅鹹呱響啞噠嘵嗶噦嘩噲嚌噥喲嘜嗊嘮啢嗩喚呼嘖嗇囀齧囉嘽嘯噴嘍嚳囁呵噯噓嚶囑嚕劈囂團園圍圇圖圓聖壙場阪壞塊堅壇壢壩塢墳墜壟壚壘墾堊墊埡墶壋塏堖塒塤堝塹墮壪牆壯聲殼壺壼複夠頭誇夾奪奐奮獎奧妝婦媽嫵嫗媯姜婁婭嬈嬌孌媧嫻嫿嬰嬋嬸媼嬡嬪嬙孫學孿甯寶實寵審憲宮寬賓寢對尋導將爾屍層屭屜屬屢屨嶼歲豈嶇崗峴嶴嵐島嶺嶽崠巋嶨嶧峽嶢嶠崢巒嶗崍嶮嶄嶸嶔嶁脊巔鞏巰幣帥師幃帳簾幟帶幀幫幬幘幗襆廣莊慶廬廡庫應廟龐廢廎開異棄張彌弳彎彈歸錄彠徹徑徠禦憶懺憂愾懷態慫憮慪悵愴憐總懟懌戀懇慟懨愷惻惱惲愨懸慳憫驚懼慘懲憊愜慚憚慣湣慍憤憒願懾憖怵懣戇戔戲戧戰紮撲擴捫掃揚擾撫拋摶摳掄搶護擔擬攏揀擁攔擰撥擇掛摯攣掗撾撻挾撓擋撟掙擠揮撏撈損撿換搗據撚擄摑擲撣摻摜摣攬撳攙擱摟攪攜攝攄擺擯攤攖攆擷擼攛擻敵斂數齋斕鬥斬斷無舊時曠暘曇晝曨顯曬曉曄暈暉暫曖術樸機殺雜權條楊榪極樅樞棗櫪梘棖槍楓梟櫃檸檉梔標棧櫛櫳棟櫨櫟欄樹棲樣欒棬椏橈楨檔榿橋樺檜槳樁檮棶檢欞槨槧欏橢樓欖櫬櫚櫸檟檻檳櫧橫檣櫻櫫櫥櫓櫞簷歟歐殲殤殘殞殮殫殯毆轂畢氈毿氌氣氫氬氳漢湯洶遝溝灃漚瀝淪滄渢溈滬濔濘淚澩瀧瀘濼瀉潑澤涇潔灑窪浹淺漿澆湞溮濁測澮濟瀏滻渾滸濃潯濜湧濤澇淶漣潿渦溳渙滌潤澗漲澀澱漬漸澠漁瀋滲溫遊灣濕潰濺漵漊潷滾滯灩灄滿瀅濾濫灤濱灘澦瀠瀟瀲濰瀦瀾瀨瀕灝燈靈燦煬爐燉煒熗點煉熾爍爛烴燭煙煩燒燁燴燙燼熱煥燜燾煆糊溜爺牘牽犧犢狀獷獁猶狽麅獮獰獨狹獅獪猙獄猻獫獵獼玀豬貓蝟獻獺璣璵瑒瑪瑋環現瑲璽瑉琺瓏璫琿璡璉瑣瓊璦璿瓔瓚甕甌電畫疇癤療瘧癘瘍鬁瘡瘋皰屙癰痙癢瘂癆瘓癇癡癉瘮瘞瘺癟癱癮癭癩癬癲臒皚皺皸盞鹽監蓋盜瞘矓著睜睞瞼瞞矚矯磯礬礦碭碼硨硯碸礪礱礫礎硜碩硤磑礄確鹼礙磧镟禮禕禰禎禱禍稟禪離稈種積稱穢穠穭穌穩穡窮竊竅窯竄窩窺竇窶豎競篤筍筆筧籠籩築篳篩簹箏籌簽簡籙簀篋籜籮簞簫簣簍籃籬籪籟糴類糶糲粵糞糧糝餱緊縶線罰罷羆羈羥羨翹翽翬耮耬聳聶聾職聹聯聵聰肅腸膚膁腎腫脹脅膽腖臚脛膠膾髒臍腦膿臠腳腡臉臘醃膕齶膩靦膃騰臏臢輿艤艦艙艫艱豔藝薌蕪蘆蓯葦藶莧萇蒼苧蘋莖蘢蔦荊薦薘莢蕘蓽蕎薈薺葷蕁藎蓀蕒葒葤藥蓧萊蓮蒔萵薟蕕鶯蓴蘀蘿螢蕭薩蔥蕆蕢蔣蔞藍薊蘺蕷鎣驀薔蘞藺藹蘄蘊藪槁蘚虜虛蟲虯蟣雖蝦蠆蝕蟻螞蠶蠔蜆蠱蠣蟶蠻蟄蛺蟯螄蠐蝸蠟蠅蟈蟬蠍螻蠑螿蟎蠨釁補襯袞襖褘襪襲襏裝襠褌褳襝褲襇褸襤觴觸觶讋譽謄諮豶赬趙趕趨趲躉躍蹌蹠躒踐躂蹺蹕躚躋踴躊蹤躓躑躡蹣躕躥躪躦軀辭辯辮邊遼達遷過邁運還這進遠違連遲邇逕適選遜遞邐邏遺鄧鄺鄔郵鄒鄴鄰鬱郤郟鄶鄭鄆酈鄖鄲醞醱醬釅釃釀釋巨鑒鑾鏨鍾鬮鬩隊陽陰陣際陸隴陳陘陝隉隕險隨隱隸雋難雛靂霧霽黴靄靚靜靨韃鞽韉韝韻颺饗饜髏髖髕魘魎黌黶黷黲齇志制只松面准拼";


	private final static HashMap<Character, Character> map = new HashMap<Character, Character>();
	
	static{
		for(int i=0;i<simpleChar.length();i++) map.put(simpleChar.charAt(i), longedChar.charAt(i));
	}
	
	public static String toLong(String str) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if(map.containsKey(ch)){
				sb.append(map.get(ch));				
			}else{
				sb.append(ch);
			}
		}
		return sb.toString();
	}
	
	public static void printExceptionWords() throws Exception{		 		
		BufferedReader br = new BufferedReader(new FileReader("./data/expcaption-reparse/data/exception(1081~end).txt"));
		String line = br.readLine();
		while(line!=null){
			Pattern p = Pattern.compile("<span>(.*?)</span>");
			Matcher m = p.matcher(line);
			while(m.find()) System.out.println(m.group(1));
			line = br.readLine();
		}
	}
	
	public static void getDefaultWordsTranslation() throws Exception{
		PrintWriter exceptionWriter = new PrintWriter(new FileOutputStream(new File("exception.txt"),true /* append = true */));
		String word = "";
								
		PrintWriter writer = new PrintWriter(new FileOutputStream(new File("output.txt"),true /* append = true */)); 		
		BufferedReader br = new BufferedReader(new FileReader("./not_found_words.txt"));
		String line;
		line = br.readLine();
		int cnt =0;
		writer.println("Date: "+ new Date());
		writer.flush();
		while(line!=null){
			cnt++;
			word = line.split("\t")[0];
			line = br.readLine();
			//if(cnt<1544) continue;
			try {								
				//WordParsed wordParsed = getTranslationApi(word);
				WordParsed wordParsed = getTranslationHtml(word);
				if(wordParsed.hasExplain==true){
					writer.print(cnt+"\t"+word);
					writer.println("\t"+wordParsed.explains+"\t"+wordParsed.phonetic);
					System.out.println(cnt+"\t"+word+"\t"+wordParsed.phonetic+"\t"+wordParsed.explains);
				}
				else{
					exceptionWriter.println(cnt+"\t<span>"+word+"</span>\tno explaination.");
					System.out.println(cnt+"\t"+word+"\tno explaination.");
					exceptionWriter.flush();
				}
					
				if(cnt%50==0) writer.flush();

			}catch (Exception e) {
				exceptionWriter.println(cnt+"\t<span>"+word+"</span>\t"+e.getClass());				
				exceptionWriter.flush();
				e.printStackTrace();				
			}
			
		}
		br.close();
		writer.close();		
		exceptionWriter.close();
	}
	
	public static WordParsed getTranslationHtml(String word) throws IOException{
		Response response;
		ArrayList<String> translations = new ArrayList<String>();

		response = Jsoup.connect("http://dict.youdao.com/search?le=eng&q="+word+"&keyfrom=dict.top").followRedirects(true).execute();
		Document xmlDoc = response.parse();
		Element transContainer = xmlDoc.getElementsByClass("trans-container").first();		
		if(xmlDoc.getElementsByClass("error-wrapper").size()==0){
			Elements translationsEle = transContainer.getElementsByTag("li");
			//System.out.println(word);
			String expJsonStr = "";
			for(Element translation : translationsEle){
				String tradition = toLong(translation.text());
				//System.out.println("   "+tradition);
				translations.add(tradition);
				JSONArray expJsonArr = new JSONArray(translations);
				expJsonStr = expJsonArr.toString();
			}
			
			String phonetic = "";
			Element wordBookEle = xmlDoc.getElementsByClass("wordbook-js").first();
			Element phoneticEle = wordBookEle.getElementsByClass("phonetic").last();
			if(phoneticEle!=null){
				phonetic = phoneticEle.text().substring(1, phoneticEle.text().length()-1);
			}
			if(expJsonStr=="") return new WordParsed(false);
			return  new WordParsed(expJsonStr, phonetic);
		}
		return new WordParsed(false);			
	}
	
	public static WordParsed getTranslationApi(String word) throws IOException, JSONException{
		
		
		String url = "http://fanyi.youdao.com/openapi.do?type=data&doctype=jsonp&version=1.1&relatedUrl=http%3A%2F%2Ffanyi.youdao.com%2Fopenapi%3Fpath%3Dweb-mode%26mode%3Ddicter&keyfrom=test&key=null&callback=c&translate=on&q="+word+"&ts=1434036010644";		
		URL obj = new URL(url);		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) response.append(inputLine);
		in.close();
				
		response.replace(0, 2, "");
		String jsonStr = response.toString();
		//System.out.println(jsonStr);
		JSONObject jsonObj = new JSONObject(jsonStr);
		if(jsonObj.has("basic")){
			JSONObject basicObj = (JSONObject) jsonObj.get("basic");
			JSONArray expJsonArr = basicObj.getJSONArray("explains");
			
			String phonetic = "";
			if(basicObj.has("us-phonetic")) phonetic = (String) basicObj.get("us-phonetic");
			con.disconnect();
			return new WordParsed(toLong(expJsonArr.toString()), phonetic);			
		}
		else if(jsonObj.has("translation")){
			String phonetic = "";
			JSONArray expJsonArr = jsonObj.getJSONArray("translation");
			return new WordParsed(toLong(expJsonArr.toString()), phonetic);	
		}
		System.out.println(jsonStr);
		con.disconnect();
		return new WordParsed(false);
	}
	
	public static class WordParsed{
		public String explains;
		public String phonetic;
		public boolean hasExplain;
		public WordParsed(String explains, String phonetic){
			this.explains = explains;
			this.phonetic = phonetic;
			hasExplain = true;
		}
		
		public WordParsed(boolean hasExplain){
			this.hasExplain = hasExplain;
			
		}				
	}
}
