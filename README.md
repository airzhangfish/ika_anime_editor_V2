# ika_anime_editor_V2
Ika动画编辑器V2（app动画动作制作工具）<br/>

if you only use this tool，you can simply download doc folder to use.
如果只是实用工具，可以直接下载doc文件夹使用打包好的工具即可。


Ika动画编辑器V2是在IKA动画编辑器V1功能的基础上增加了许多功能而形成的新版本<br/>
80%的代码重新编写,代码结构也和原来的结构完全不同.无论是速度,稳定性还是扩展性上,都是很大的进步.(毕竟V1版本是08年前开发的,针对N年前的kjava和简单桌面java游戏使用)V2版本最大的特性是支持缩放和旋转,并且全面支持android和iphone手机动画的使用.支持50000x50000以上的图的处理和解析
因为增加新的功能,所以和老版本的格式略有不同,因此不能和V1版本的数据文件通用. <br/>
<img src="https://github.com/airzhangfish/ika_anime_editor_V2/blob/master/doc/samplepic/xx1.jpg" />
<img src="https://github.com/airzhangfish/ika_anime_editor_V2/blob/master/doc/samplepic/xx2.gif" /><br/>
<img src="https://github.com/airzhangfish/ika_anime_editor_V2/blob/master/doc/samplepic/xx3.jpg" /><br/>
<img src="https://github.com/airzhangfish/ika_anime_editor_V2/blob/master/doc/samplepic/xx4.jpg" /><br/>
<br/>
How to Use:<br/>
创建新动画:<br/>
1.打开动画编辑器<br/>
2.点击 物块编辑分页-->读取图片按钮,读取一张PNG图片(整图)<br/>
3.用鼠标切割图片成小mod物块<br/>
4.切换到帧编辑.点击添加帧(尽量将屏幕放全屏,有些按钮才能看到)<br/>
5.在选中帧的情况下,用物块拼装当前帧的页面<br/>
6.不断的添加帧,对帧进行编辑.<br/>
7.在编辑完毕所有帧的情况下,进入动画编辑页面,添加一个动画<br/>
8.在选中动画的情况下,把帧加入动画中.<br/>
9.点击演示就可以演示动画.<br/>
10.保存xml文件.这个xml文件是应用于在PC上编辑的保存格式. <br/>
<br/>
编辑器读取动画:<br/>
1.打开动画编辑器<br/>
2.选择菜单中的读取xml,然后点击一下左边列表的图片,确认载入正常.<br/>
3.动画载入完毕 <br/>
<br/>
打包动画到手机上运行:<br/>
1.当动画编辑完毕后,按步骤执行以下.<br/>
2.导出IKA文件<br/>
3.导出图片文件,如果机器有PNGMATE的话,建议运行生成的bat文件(无pngmate的话禁用)<br/>
4.打包图片.<br/>
5.此时生成的2个文件,simple.ika和imgpak.bin,这2个文件,将这2个文件copy进入程序目录调用.<br/>

6-1:android客户端使用方法：下载doc/手机使用读取代码android.7z 使用<br/><br/>
6-2:IOS客户端使用方法：<br/><br/>
6-3:.J2ME客户端使用方法: 创建方法:<br/>
AnimeEngine.imgSprite_pool[0]=new AnimeSprite("/logo.ika","/logo.bin");<br/>
GSprite gsprite=new GSprite(104,136,0,0,true);<br/>
使用方法: gsprite.paint(g);<br/>
清理内存方法: gsprite=null;<br/>
AnimeEngine.imgSprite_pool[0].setNull();<br/>
AnimeEngine.imgSprite_pool[0]=null;<br/>
System.gc();<br/>
