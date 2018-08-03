package com.vero.dm.web.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  20:32 2017/12/14.
 * @since easyclass
 */

@Profile(value = {"prod","dev"})
@EnableSwagger2
@Configuration
public class Swagger2Configuration {
    private static final String SWAGGER_SCAN_BASE_PACKAGE = "com.vero.dm.api";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

//    @Bean
//    public Docket swaggerSpringMvcPlugin() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
//                .paths(PathSelectors.any())
//                .build();
//    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("数据挖掘实践教学平台RESTful APIs说明文档")
                .description("本文档说明了每个接口的输入参数，及其输出的JSON数据形式。" +
                        "此文档是前后端分离模式开发的标准，要求前端发送符合输入参数的HTTP请求，让每个请求携带着符合服务器要求的用户授权信息，经服务器授权通过后返回JSON数据流。" +
                        "每个数据的返回格式基本是基于后台的数据库Entity序列化过来的，但也有一部分数据是基于过滤、组合、拆分、优化、统计得到的DTO序列化过来的。" +
                        "比如学生、教师用户的信息包含密码等敏感信息，后端用StudentDTO过滤了一部分敏感字段。" +
                        "一个实践任务会统计其所属的分组学生上交数据挖掘结果MiningResult的情况，后端的业务逻辑将其组织成TaskStatistics封装成api返回。" +
                        "一些实体由于关联复杂，且后台的全自动化ORM工具Hibernate的延迟加载(Lazy Loading)会导致一些异常，系统可能无法将一个实体及其关联实体完全序列化成JSON。" +
                        "一些前台的请求也并不是想要获取到该实体全部的字段信息(而是其中的一部分)，所以为了兼顾性能、稳定、粒度，系统有时候将数据关联拆分成更多的RESTful APIs。" +
                        "如实践任务DataMiningTask跟其他实体如分组、数据集、任务阶段、挖掘结果等Entities关联,含有的信息字段比较多，一般拆分成多个APIs获取其关联的对象。" +
                        "具体的形式风格式遵循RESTful语义的，获取一个实践任务的关联分组,API的URL表现为'/tasks/{taskId}/groups',其中taskId为逻辑主键的值，也就是任务的标识。" +
                        "类似的,获取一个实践任务的任务阶段为'/tasks/{taskId}/stages'。也就是说，这些API遵守RESTful的语义，且大多遵循页面逻辑。因为用户一般从一个概览的页面跳转到一个具体一点的页面，" +
                        "或者从具体的页面跳转到详情的页面，伴随着这些页面逻辑，前端可以通过REST的请求在上面一个一个页面得到了一级一级的实体信息。" +
                        "即前端需要获取一个实体Entity关联的下一个实体时，一般先获取到其ID，用API级联查询的方式一层一层地深入下去。" +
                        "举个简单的业务场景,教师管理员登录系统后，点开了任务管理模块的概览界面，这时候需要(分页)查询出任务列表，当然是通过GET动词的'/tasks'获取到数据。" +
                        "数据形式是这样的,[{taskId:...;taskName:...;...},{taskId:...};taskName:...;...]。显然，前端已经得到了tasks的taskIds了。" +
                        "这时，用户又想看看这个实践任务下的详细信息。点击了前端页面的'详情'按钮进入了详情页，前端自然需要更多的信息填充这个页面了。" +
                        "如任务关联的分组、任务阶段，前端需要发起'/tasks/{taskId}/groups','/tasks/{taskId}/stages'获取数据。" +
                        "然而，用户还想知道这个任务关联分组的组员有谁，自然需要刚刚得到的新鲜滚热辣的groupId去访问'/groups/{groupId}/members',还可以访问'/groups/{groupId}'得到这个任务关联分组的基本信息。" +
                        "一般情况系统提供了常用的级联查询，也就是系统几乎为每一个Entity都设计了一个Controller暴露API，以便上述的查询语义可以持续生效。" +
                        "但有一些情况，级联查询的次数太多，请求的开销大而繁复，后端应当根据需求开发出可以快速响应的接口，达到一次性获取到特定数据的目的" +
                        "假如前端要请求到特定任务，一个学生的所有未交的、新提交、已下载的数据挖掘记录，以便一次下下载全部内容该怎么办?" +
                        "自然，后端需要设计一个形如'/tasks/{taskId}/result_records'的接口返回数据。注意，我们对API设计时，'/'后面的实体并不死死的遵守需要与前面的task进行直接关联的语义，它有可能是间接的，往下跨越几层关联的。" +
                        "这里的result_records是某种意义上抽象出来的资源resources,因为它并不直接跟task关联，而是间接的关联。ResultRecord的父亲(直接关联者)是MiningResult(因为一个结果可以被多次提交，产出多条记录),MiningResult的父亲则是MiningStage," +
                        "MiningStage的父亲才是DataMiningTask。这样，还需要遵循上面提及的级联查询的话，我们的逻辑是这样的'task--->stage--->result--->record'。" +
                        "途经的API依次是'/tasks'------>'/tasks/{taskId}/stages'----->'/stages/{stageId}/results'(这个API可能还得用几次，因为任务对阶段是一对多关系)--->'/results/{resultId}/result_records'。" +
                        "而且伴随的一对多的关系的叠加，查询的次数是以乘法计算的，显然这样的设计效果会带来粒度划分过细的问题，无法让前端访问到直接、有效的数据。" +
                        "'/tasks/{taskId}/result_records'类型的API就是为了解决跨越式查询而诞生的。" +
                        "总而言之，在RESTful API万物皆资源的体系框架下，后端的实体也被抽象为资源，只是资源可能是单一的，也可以是被组合优化过的，也可能是跨越了多个资源联结统计出来的，对前端暴露出来的无非就是一个一个名词。前端通过动词的HTTP方法，得到名词化的JSON资源，仅此而已。")
                .version("2.0")
                .contact(new Contact("刘祥德","/","luvletterU@njust.edu.cn"))
                .build();
    }
}
