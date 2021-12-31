/**
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><a href="https://refactoring.guru/ru/design-patterns/command"><strong>
 * Команда</strong></a></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * — это поведенческий паттерн проектирования, который превращает запросы в объекты, позволяя передавать их как аргументы
 * при вызове методов, ставить запросы в очередь, логировать их, а также поддерживать отмену операций.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Применимость</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>* Когда вы хотите параметризовать объекты выполняемым действием.
 * <ul>Команда превращает операции в объекты. А объекты можно передавать, хранить и взаимозаменять внутри других объектов.</ul>
 * <ul>Скажем, вы разрабатываете библиотеку графического меню и хотите, чтобы пользователи могли использовать меню в
 * разных приложениях, не меняя каждый раз код ваших классов. Применив паттерн, пользователям не придётся изменять
 * классы меню, вместо этого они будут конфигурировать объекты меню различными командами.</ul>
 * <p>* Когда вы хотите ставить операции в очередь, выполнять их по расписанию или передавать по сети.
 * <ul>Как и любые другие объекты, команды можно сериализовать, то есть превратить в строку, чтобы потом сохранить в
 * файл или базу данных. Затем в любой удобный момент её можно достать обратно, снова превратить в объект команды и
 * выполнить. Таким же образом команды можно передавать по сети, логировать или выполнять на удалённом сервере.</ul>
 * <p>* Когда вам нужна операция отмены.
 * <ul>Главная вещь, которая вам нужна, чтобы иметь возможность отмены операций, — это хранение истории. Среди многих
 * способов, которыми можно это сделать, паттерн Команда является, пожалуй, самым популярным.</ul>
 * <ul>История команд выглядит как стек, в который попадают все выполненные объекты команд. Каждая команда перед
 * выполнением операции сохраняет текущее состояние объекта, с которым она будет работать. После выполнения операции
 * копия команды попадает в стек истории, все ещё неся в себе сохранённое состояние объекта. Если потребуется отмена,
 * программа возьмёт последнюю команду из истории и возобновит сохранённое в ней состояние.</ul>
 * <ul>Этот способ имеет две особенности. Во-первых, точное состояние объектов не так-то просто сохранить, ведь часть
 * его может быть приватным. Но с этим может помочь справиться паттерн <a href="https://refactoring.guru/ru/design-patterns/memento"><strong>Снимок</strong></a>.</ul>
 * <ul>Во-вторых, копии состояния могут занимать довольно много оперативной памяти. Поэтому иногда можно прибегнуть к
 * альтернативной реализации, когда вместо восстановления старого состояния команда выполняет обратное действие.
 * Недостаток этого способа в сложности (а иногда и невозможности) реализации обратного действия.</ul>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Шаги реализации</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>1. Создайте общий интерфейс команд и определите в нём метод запуска.
 * <p>2. Один за другим создайте классы конкретных команд. В каждом классе должно быть поле для хранения ссылки на
 * один или несколько объектов-получателей, которым команда будет перенаправлять основную работу.
 * <ul>Кроме этого, команда должна иметь поля для хранения параметров, которые нужны при вызове методов получателя.
 * Значения всех этих полей команда должна получать через конструктор.</ul>
 * <ul>И, наконец, реализуйте основной метод команды, вызывая в нём те или иные методы получателя.</ul>
 * <p>3. Добавьте в классы отправителей поля для хранения команд. Обычно объекты-отправители принимают готовые объекты
 * команд извне — через конструктор либо через сеттер поля команды.
 * <p>4. Измените основной код отправителей так, чтобы они делегировали выполнение действия команде.
 * <p>5. Порядок инициализации объектов должен выглядеть так:
 * <ul><ul>
 * <li>Создаём объекты получателей.</li>
 * <li>Создаём объекты команд, связав их с получателями.</li>
 * <li>Создаём объекты отправителей, связав их с командами.</li>
 * </ul></ul>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Преимущества и недостатки</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>"+": убирает прямую зависимость между объектами, вызывающими операции, и объектами, которые их непосредственно
 * выполняют.
 * <p>"+": позволяет реализовать простую отмену и повтор операций.
 * <p>"+": позволяет реализовать отложенный запуск операций.
 * <p>"+": позволяет собирать сложные команды из простых.
 * <p>"+": реализует принцип открытости/закрытости.
 * <p>"-": усложняет код программы из-за введения множества дополнительных классов.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Отношения с другими паттернами</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <ul>
 * <li><strong>Цепочка обязанностей</strong>, <strong>Команда</strong>, <strong>Посредник</strong> и
 * <strong>Наблюдатель</strong> показывают различные способы работы отправителей запросов с их получателями:
 * <ul>
 * <li><i>Цепочка обязанностей</i> передаёт запрос последовательно через цепочку потенциальных получателей, ожидая,
 * что какой-то из них обработает запрос.</li>
 * <li><i>Команда</i> устанавливает косвенную одностороннюю связь от отправителей к получателям.</li>
 * <li><i>Посредник</i> убирает прямую связь между отправителями и получателями, заставляя их общаться опосредованно,
 * через себя.</li>
 * <li><i>Наблюдатель</i> передаёт запрос одновременно всем заинтересованным получателям, но позволяет им динамически
 * подписываться или отписываться от таких оповещений.</li>
 * </ul>
 * </li>
 * <li>Обработчики в <strong>Цепочке обязанностей</strong> могут быть выполнены в виде <strong>Команд</strong>. В этом
 * случае множество разных операций может быть выполнено над одним и тем же контекстом, коим является запрос.
 * <ul><li>Но есть и другой подход, в котором сам запрос является Командой, посланной по цепочке объектов. В этом случае
 * одна и та же операция может быть выполнена над множеством разных контекстов, представленных в виде цепочки.</li></ul>
 * </li>
 * <li><strong>Команду</strong> и <strong>Снимок</strong> можно использовать сообща для реализации отмены операций.
 * В этом случае объекты команд будут отвечать за выполнение действия над объектом, а снимки будут хранить резервную
 * копию состояния этого объекта, сделанную перед самым запуском команды.</li>
 * <li><strong>Команда</strong> и <strong>Стратегия</strong> похожи по духу, но отличаются масштабом и применением:
 * <ul>
 * <li><i>Команду</i> используют, чтобы превратить любые разнородные действия в объекты. Параметры операции превращаются
 * в поля объекта. Этот объект теперь можно логировать, хранить в истории для отмены, передавать во внешние сервисы и так далее.</li>
 * <li>С другой стороны, <i>Стратегия</i> описывает разные способы произвести одно и то же действие, позволяя
 * взаимозаменять эти способы в каком-то объекте контекста.</li>
 * </ul></li>
 * <li>Если <strong>Команду</strong> нужно копировать перед вставкой в историю выполненных команд, вам может помочь
 * <strong>Прототип</strong>.</li>
 *
 * <li><strong>Посетитель</strong> можно рассматривать как расширенный аналог <strong>Команды</strong>,
 * который способен работать сразу с несколькими видами получателей.</li>
 * </ul>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Примеры Стратегии в стандартных библиотеках Java:</strong></p>
 * --------------------------------------------------------------------------------------------------------------------
 * <p>* <strong>java.util.Comparator#compare()</strong>, вызываемые из Collections#sort().
 * <p>* <strong>javax.servlet.http.HttpServlet</strong>: метод service(), а также все методы doXXX() принимают объекты
 * HttpServletRequest и HttpServletResponse в параметрах.
 * <p>* <strong> javax.servlet.Filter#doFilter()</strong>
 */
package patterns.refactoring.guru.behavioral.command;