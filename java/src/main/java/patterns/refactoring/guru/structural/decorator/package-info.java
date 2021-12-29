/**
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><a href="https://refactoring.guru/ru/design-patterns/decorator"><strong>Декоратор</strong></a></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>это структурный паттерн, который позволяет:
 * <p>- добавлять объектам новые поведения на лету,
 * помещая их в объекты-обёртки.
 * <p>- оборачивать объекты бесчисленное количество раз благодаря тому, что и обёртки, и реальные оборачиваемые
 * объекты имеют общий интерфейс.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Применимость</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p> *Когда вам нужно добавлять обязанности объектам на лету, незаметно для кода, который их использует.
 * <p>Объекты помещают в обёртки, имеющие дополнительные поведения. Обёртки и сами объекты имеют одинаковый интерфейс,
 * поэтому клиентам без разницы, с чем работать — с обычным объектом данных или с обёрнутым.
 * <p> *Когда нельзя расширить обязанности объекта с помощью наследования.
 * <p>Во многих языках программирования есть ключевое слово final, которое может заблокировать наследование класса.
 * Расширить такие классы можно только с помощью Декоратора.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Шаги реализации</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>1. Убедитесь, что в вашей задаче есть один основной компонент и несколько опциональных дополнений или надстроек над
 * ним.
 * <p>2. Создайте интерфейс компонента, который описывал бы общие методы как для основного компонента, так и для его
 * дополнений.
 * <p>3. Создайте класс конкретного компонента и поместите в него основную бизнес-логику.
 * <p>4. Создайте базовый класс декораторов. Он должен иметь поле для хранения ссылки на вложенный объект-компонент.
 * Все методы базового декоратора должны делегировать действие вложенному объекту.
 * <p>5. И конкретный компонент, и базовый декоратор должны следовать одному и тому же интерфейсу компонента.
 * <p>6. еперь создайте классы конкретных декораторов, наследуя их от базового декоратора. Конкретный декоратор должен
 * выполнять свою добавочную функцию, а затем (или перед этим) вызывать эту же операцию обёрнутого объекта.
 * <p>7. Клиент берёт на себя ответственность за конфигурацию и порядок обёртывания объектов.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Преимущества и недостатки</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>"+": Большая гибкость, чем у наследования.
 * <p>"+": Позволяет добавлять обязанности на лету.
 * <p>"+": Можно добавлять несколько новых обязанностей сразу.
 * <p>"+": Позволяет иметь несколько мелких объектов вместо одного объекта на все случаи жизни.
 * <p>"-": Трудно конфигурировать многократно обёрнутые объекты.
 * <p>"-": Обилие крошечных классов.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Отношения с другими паттернами</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>* Адаптер меняет интерфейс существующего объекта. Декоратор улучшает другой объект без изменения его интерфейса. Причём Декоратор поддерживает рекурсивную вложенность, чего не скажешь об Адаптере.
 * <p>* Адаптер предоставляет классу альтернативный интерфейс. Декоратор предоставляет расширенный интерфейс. Заместитель предоставляет тот же интерфейс.
 * <p>* Цепочка обязанностей и Декоратор имеют очень похожие структуры. Оба паттерна базируются на принципе рекурсивного выполнения операции через серию связанных объектов. Но есть и несколько важных отличий.
 * <ul>Обработчики в Цепочке обязанностей могут выполнять произвольные действия, независимые друг от друга, а также в любой момент прерывать дальнейшую передачу по цепочке. С другой стороны Декораторы расширяют какое-то определённое действие, не ломая интерфейс базовой операции и не прерывая выполнение остальных декораторов.</ul>
 * <p>* Компоновщик и Декоратор имеют похожие структуры классов из-за того, что оба построены на рекурсивной вложенности. Она позволяет связать в одну структуру бесконечное количество объектов.
 * <ul>Декоратор оборачивает только один объект, а узел Компоновщика может иметь много детей. Декоратор добавляет вложенному объекту новую функциональность, а Компоновщик не добавляет ничего нового, но «суммирует» результаты всех своих детей.
 * <p>Но они могут и сотрудничать: Компоновщик может использовать Декоратор, чтобы переопределить функции отдельных частей дерева компонентов.</ul>
 * <p>* Архитектура, построенная на Компоновщиках и Декораторах, часто может быть улучшена за счёт внедрения Прототипа. Он позволяет клонировать сложные структуры объектов, а не собирать их заново.
 * <p>* Стратегия меняет поведение объекта «изнутри», а Декоратор изменяет его «снаружи».
 * <p>* Декоратор и Заместитель имеют схожие структуры, но разные назначения. Они похожи тем, что оба построены на принципе композиции и делегируют работу другим объектам. Паттерны отличаются тем, что Заместитель сам управляет жизнью сервисного объекта, а обёртывание Декораторов контролируется клиентом.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Примеры Декораторов в стандартных библиотеках Java:</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>- все подклассы java.io.InputStream, OutputStream, Reader и Writer имеют конструктор, принимающий объекты этих же классов.
 * <p>- java.util.Collections, методы checkedXXX(), synchronizedXXX() и unmodifiableXXX().
 * <p>- javax.servlet.http.HttpServletRequestWrapper и HttpServletResponseWrapper
 *
 */
package patterns.refactoring.guru.structural.decorator;