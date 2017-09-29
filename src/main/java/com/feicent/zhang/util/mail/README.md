# mail-util
邮件发送工具类<br/>
本工具类提供两种方式发送邮件<br/>
1.发送文本邮件<br/>
2.发送html模版邮件<br/>

使用方式<br/>
a. 添加jar包依赖<br/>
     <dependency><br/>
         <groupId>com.feicent.zhang</groupId><br/>
         <artifactId>mail-util</artifactId><br/>
         <version>1.0-SNAPSHOT</version><br/>
     </dependency><br/>
     
b. 配置文件中填写邮箱信息<br/>
在类根目录创建配置文件important.properties<br/>
填写配置信息如下<br/>

mail.host=smtp.163.com<br/>
mail.port=25<br/>
mail.username=helloworld<br/>
mail.password=20170101@zhang<br/>
mail.from=helloworld@163.com<br/>

c. 创建邮件信息类，使用工具类进行发送

     @Test
     public void testSend() throws Exception {
         TextMailMessage message = new TextMailMessage();
         message.setTo("80****692@qq.com");
         message.setSubject("测试邮件工具类");
         message.setText("这里是邮件发送工具类的正文内容");
         MailUtil.send(message);
     }

     @Test
     public void testSend1() throws Exception {
         TemplateMailMessage message = new TemplateMailMessage();
         message.setTo("80****692@qq.com");
         message.setSubject("测试邮件工具类");
         Map model = new HashMap();
         model.put("user", "用户名");
         model.put("text","这里是通过模版输出的内容 !");
         message.setModel(model);
         message.setTemplatePath("template/demo.vm");
         MailUtil.send(message);
     }
