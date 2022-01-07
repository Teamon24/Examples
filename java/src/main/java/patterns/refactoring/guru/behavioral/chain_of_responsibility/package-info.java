/**
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><a href="https://refactoring.guru/ru/design-patterns/chain-of-responsibility"><strong>Цепочка обязанностей</strong></a></p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p>— это поведенческий паттерн проектирования, который позволяет передавать запросы последовательно по цепочке обработчиков. Каждый последующий обработчик решает, может ли он обработать запрос сам и стоит ли передавать запрос дальше по цепи.
 * --------------------------------------------------------------------------------------------------------------------
 * <p><strong>Применимость</strong></p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p>* Когда программа должна обрабатывать разнообразные запросы несколькими способами, но заранее неизвестно,
 * какие конкретно запросы будут приходить и какие обработчики для них понадобятся.
 * <ul>С помощью Цепочки обязанностей вы можете связать потенциальных обработчиков в одну цепь и при получении
 * запроса поочерёдно спрашивать каждого из них, не хочет ли он обработать запрос.</ul>
 * <p>* Когда важно, чтобы обработчики выполнялись один за другим в строгом порядке.
 *  <ul>Цепочка обязанностей позволяет запускать обработчиков последовательно один за другим в том порядке,
 *  в котором они находятся в цепочке.</ul>
 * <p>* Когда набор объектов, способных обработать запрос, должен задаваться динамически.
 *  <ul>В любой момент вы можете вмешаться в существующую цепочку и переназначить связи так,
 *  чтобы убрать или добавить новое звено.</ul>
 * --------------------------------------------------------------------------------------------------------------------
 * <p><strong>Шаги реализации</strong></p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p>1. Создайте интерфейс обработчика и опишите в нём основной метод обработки.
 * <ul>Продумайте, в каком виде клиент должен передавать данные запроса в обработчик. Самый гибкий способ —
 * превратить данные запроса в объект и передавать его целиком через параметры метода обработчика.</ul>
 * <p>2. меет смысл создать абстрактный базовый класс обработчиков, чтобы не дублировать реализацию метода получения
 * следующего обработчика во всех конкретных обработчиках.
 * <ul>Добавьте в базовый обработчик поле для хранения ссылки на следующий объект цепочки. Устанавливайте начальное
 * значение этого поля через конструктор. Это сделает объекты обработчиков неизменяемыми. Но если программа предполагает
 * динамическую перестройку цепочек, можете добавить и сеттер для поля.</ul>
 * <ul>Реализуйте базовый метод обработки так, чтобы он перенаправлял запрос следующему объекту, проверив его наличие.
 * Это позволит полностью скрыть поле-ссылку от подклассов, дав им возможность передавать запросы дальше по цепи,
 * обращаясь к родительской реализации метода.</ul>
 * <p>3. Один за другим создайте классы конкретных обработчиков и реализуйте в них методы обработки запросов.
 * При получении запроса каждый обработчик должен решить:
 * <ul>
 * <li>Может ли он обработать запрос или нет?</li>
 * <li>Следует ли передать запрос следующему обработчику или нет?</li>
 * </ul>
 * <p>4. Клиент может собирать цепочку обработчиков самостоятельно, опираясь на свою бизнес-логику, либо получать
 * уже готовые цепочки извне. В последнем случае цепочки собираются фабричными объектами, опираясь на конфигурацию
 * приложения или параметры окружения.
 * <p>5. Клиент может посылать запросы любому обработчику в цепи, а не только первому. Запрос будет передаваться
 * по цепочке до тех пор, пока какой-то обработчик не откажется передавать его дальше, либо когда будет достигнут
 * конец цепи.
 * <p>6. Клиент должен знать о динамической природе цепочки и быть готов к таким случаям:
 * <ul>
 * <li>Цепочка может состоять из единственного объекта.</li>
 * <li>Запросы могут не достигать конца цепи.</li>
 * <li>Запросы могут достигать конца, оставаясь необработанными.</li>
 * </ul>
 * --------------------------------------------------------------------------------------------------------------------
 * <p><strong>Преимущества и недостатки</strong></p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p>"+": уменьшает зависимость между клиентом и обработчиками.
 * <p>"+": реализует принцип единственной обязанности.
 * <p>"+": реализует принцип открытости/закрытости.
 * <p>"-": запрос может остаться никем не обработанным.
 * --------------------------------------------------------------------------------------------------------------------
 * <p><strong>Отношения с другими паттернами</strong></p>
 * --------------------------------------------------------------------------------------------------------------------
 * <ul><li>Цепочка обязанностей, Команда, Посредник и Наблюдатель показывают различные способы работы отправителей
 * запросов с их получателями:</li>
 * <ul>
 * <li>Цепочка обязанностей передаёт запрос последовательно через цепочку потенциальных получателей, ожидая, что
 * какой-то из них обработает запрос.</li>
 * <li>Команда устанавливает косвенную одностороннюю связь от отправителей к получателям.</li>
 * <li>Посредник убирает прямую связь между отправителями и получателями, заставляя их общаться опосредованно,
 * через себя.</li>
 * <li>Наблюдатель передаёт запрос одновременно всем заинтересованным получателям, но позволяет им динамически
 * подписываться или отписываться от таких оповещений.</li>
 * </ul>
 * <li>Цепочку обязанностей часто используют вместе с Компоновщиком. В этом случае запрос передаётся от дочерних
 * компонентов к их родителям.</li>
 * <li>Обработчики в Цепочке обязанностей могут быть выполнены в виде Команд. В этом случае множество разных операций
 * может быть выполнено над одним и тем же контекстом, коим является запрос.</li>
 * <ul>Но есть и другой подход, в котором сам запрос является Командой, посланной по цепочке объектов. В этом случае
 * одна и та же операция может быть выполнена над множеством разных контекстов, представленных в виде цепочки.</ul>
 * <li>Цепочка обязанностей и Декоратор имеют очень похожие структуры. Оба паттерна базируются на принципе рекурсивного
 * выполнения операции через серию связанных объектов. Но есть и несколько важных отличий.</li>
 * <ul>Обработчики в Цепочке обязанностей могут выполнять произвольные действия, независимые друг от друга, а также
 * в любой момент прерывать дальнейшую передачу по цепочке. С другой стороны Декораторы расширяют какое-то определённое
 * действие, не ломая интерфейс базовой операции и не прерывая выполнение остальных декораторов.</ul>
 * </ul>
 * --------------------------------------------------------------------------------------------------------------------
 * <p><strong>Примеры Цепочки обязанностей в стандартных библиотеках Java:</strong></p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p>*<strong>java.util.logging.Logger#log()</strong>
 * <p>*<strong>javax.servlet.Filter#doFilter()</strong>
 */
package patterns.refactoring.guru.behavioral.chain_of_responsibility;