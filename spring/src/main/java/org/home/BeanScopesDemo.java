package org.home;

import org.apache.commons.lang3.StringUtils;
import org.home.components.scopes.common.CommandManager;
import org.home.components.scopes.common.SomeService;
import org.home.configs.common.CommonConfiguration;
import org.home.model.SomeEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BeanScopesDemo {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CommonConfiguration.class);

        SomeService<SomeEntity> someServiceImpl = applicationContext.getBean("someServiceImpl", SomeService.class);
        SomeService<SomeEntity> manuallySomeService = applicationContext.getBean("manuallyCreatedSomeService", SomeService.class);

        System.out.println(someServiceImpl.list());
        System.out.println(manuallySomeService.list());

        List<CommandManager> prototypes = beans(10, BeanScopesDemo::prototype, applicationContext);
        List<CommandManager> singletons = beans(10, BeanScopesDemo::singleton, applicationContext);

        System.out.printf("prototypes are different: %s, ids %s\n", allDifferent(prototypes), ids(prototypes));
        System.out.printf("singletons are same: %s, ids %s\n", allEqual(singletons), ids(singletons));
    }

    private static Boolean allDifferent(List<CommandManager> commandManagers) {
        return new HashSet<>(commandManagers).size() == commandManagers.size();
    }

    private static Boolean allEqual(List<CommandManager> commandManagers) {
        return new HashSet<>(commandManagers).size() == 1;
    }

    private static String ids(List<CommandManager> commandManagers) {
        return StringUtils.joinWith(",", commandManagers.stream().map(cm -> cm.id).collect(Collectors.toList()));
    }

    private static <R> List<R> beans(
        int quantity,
        Function<ApplicationContext, R> creator,
        ApplicationContext context
    ) {
        ArrayList<R> beans = new ArrayList<>();
        for (int j = 0; j < quantity; j++) {
            beans.add(creator.apply(context));
        }
        return beans;
    }

    private static CommandManager prototype(ApplicationContext applicationContext) {
        return applicationContext.getBean("prototypeCommandManager", CommandManager.class);
    }

    private static CommandManager singleton(ApplicationContext applicationContext) {
        return applicationContext.getBean("singletonCommandManager", CommandManager.class);
    }
}
