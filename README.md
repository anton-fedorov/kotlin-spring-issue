# kotlin-spring-issue
Reference repository to reproduce issue with kotlin-spring plugin.

# Issue description

Root `build.gradle` file defines `buildscript` from external script plugin via:
```
apply from: 'local_plugin.gradle'
```
In this case, `kotlin-spring` plugin doesn't work properly, as main `@SpringBootApplication` class 
`HelloNotWorkingApp` keeps being final, while it should become open during compilation.

## Reproduce issue

```
./gradlew build -b build.gradle --rerun-tasks
```

Unit test will fail, since it is not able to start Spring context, due to error below.
Which actually means, that `kotlin-spring` plugin didn't properly work, and `HelloNotWorkingApp` 
class wasn't changed to be open.

```
Caused by: org.springframework.beans.factory.parsing.BeanDefinitionParsingException: Configuration problem: @Configuration class 'HelloNotWorkingApp' may not be final. Remove the final modifier to continue.
Offending resource: io.test.kotlin_spring_issue.HelloNotWorkingApp
	at org.springframework.beans.factory.parsing.FailFastProblemReporter.error(FailFastProblemReporter.java:70)
	at org.springframework.context.annotation.ConfigurationClass.validate(ConfigurationClass.java:214)
	at org.springframework.context.annotation.ConfigurationClassParser.validate(ConfigurationClassParser.java:207)
	at org.springframework.context.annotation.ConfigurationClassPostProcessor.processConfigBeanDefinitions(ConfigurationClassPostProcessor.java:309)
	at org.springframework.context.annotation.ConfigurationClassPostProcessor.postProcessBeanDefinitionRegistry(ConfigurationClassPostProcessor.java:228)
	at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanDefinitionRegistryPostProcessors(PostProcessorRegistrationDelegate.java:270)
	at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(PostProcessorRegistrationDelegate.java:93)
	at org.springframework.context.support.AbstractApplicationContext.invokeBeanFactoryPostProcessors(AbstractApplicationContext.java:686)
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:524)
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:737)
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:370)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:314)
	at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:120)
	at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:98)
	at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:116)
	... 52 more
```

# When it works fine

However if instead of referring to external script file, `buildscript` is simply put to main `build.gradle`,
 then things work ok and `HelloNotWorkingApp` is properly made open via `kotlin-spring` plugin.
 
## Command to successfully launch

```
./gradlew build -b build-ok.gradle --rerun-tasks
```

With this command, test successfully passes, which means that `kotlin-spring` plugin worked fine

## Case with subproject

It seems that if you do the same trick with referring to `local_plugin.gradle` from subproject `build.gradle` 
instead of root `build.gradle`, then it works fine. 
Maybe `SpringGradleSubplugin` currently works only with subprojects? 

# Notes
Note, that since gradle spring plugin is used, we can't refer to `kotlin` and `kotlin-spring` plugins by names, so full class names are applied
