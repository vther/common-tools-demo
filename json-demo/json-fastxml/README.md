Jackson fasterxml和codehaus的区别 （fasterxml vs. codehaus）

Jackson fasterxml和codehaus的区别：
他们是Jackson的两大分支、也是两个版本的不同包名。Jackson从2.0开始改用新的包名fasterxml；1.x版本的包名是codehaus。除了包名不同，他们的Maven artifact id也不同。1.x版本现在只提供bug-fix，而2.x版本还在不断开发和发布中。如果是新项目，建议直接用2x，即fasterxml jackson。


Jackson可以轻松的将Java对象转换成json对象和xml文档，同样也可以将json、xml转换成Java对象。
官网：http://wiki.fasterxml.com/JacksonHome

三个主要的模块：
jackson-core：核心包
jackson-annotations：注解包
jackson-databind：数据绑定包
jackson-databind需要引用另外两个包，所以如果项目中需要jackson-databind，则只需要加入它的dependency就行了，其他两个会自动引入：
 <dependency>
   <groupId>com.fasterxml.jackson.core</groupId>
   <artifactId>jackson-databind</artifactId>
   <version>2.5.3</version>
 </dependency>

 资源

 Jackson Project Home @github
 jackson-core @github
 jackson-annotations@github | 各种annotations介绍@github
 jackson-databind@github
 FasterXML/jackson-docs@github
 Java JSON Tutorial 很详细的教程
 Eugen Paraschiv JSON Blog教程，对各种特性都有介绍且有简单例子

 FastXML Jackon最佳实践

 原文Jackson Best Practices: Performance
 简单总结
 -尽可能重用ObjectMapper及JsonFactory
 使用后清理
 作为惯例，对象使用后要清理，关闭。如JsonParser及JsonGenerator
 清理工作能释放及重用一些内部资源。对一些短小的JSON Document特别有用。
 使用最原始的输入源
 Jackson已经对各种输入源的格式做了优化，能有效且正确地转换、解码。
 假如使用者有InputStream，不需转为InputStreamReader。
 如果使用者有File或URL，只要直接交给Jackon就行了。
 不要缓存输入源。
 无需预先处理数据，Jackson干得比你高效，人家是专家。
 必要时才修改默认设置
 默认设置已经是先优性能。启用一些特性会带来性能影响，所以不是特别需要就不要修改。
 可再处理、回放，但不要多次解释

 If you need to re-process, replay, don’t re-parse
 假如不能一下子全部处理完JSON的内容，不应该也不能把中间产物还原JSON文本，即使Jackson足够快，但这些操作的消耗比较大的。
 -如果你需要JSON Tree做一些事情，可以通过调用 JsonParser jp = treeRoot.traverse()来构建JsonParser
 -如果需要多次读取同一个流的内容，可能使用org.codehaus.jackson.util.TokenBuffer，反复读取它的消耗都比较小。
 此外，TokenBuffer实现了JsonGenerator接口, 可以用TokenBuffer串行化POJOs:objectMapper.writeValue(tokenBuffer, bean)
 当内容需要递增地处理时，使用JsonNode或TokenBuffer能很好地提高性能。
 此外，因为有大量各种的转换器，写出的代码将会是简单和易懂的。
 静态类型
 静态类型能轻微提高性能。
 优先ObjectReader/ObjectWriter而非ObjectMapper
 主要是为了线程安全，但对性能也有帮助。
 使用ObjectReader.readValues生成字符串
 在读取顶层POJO时，ObjectReader的readValues()的方法比使用ObjectMapper.readValue()更有效.