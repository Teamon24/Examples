/**
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><a href="https://refactoring.guru/ru/design-patterns/observer"><strong>Наблюдатель</strong></a></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * — это поведенческий паттерн проектирования, который создаёт механизм подписки, позволяющий одним объектам следить
 * и реагировать на события, происходящие в других объектах.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Применимость</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>* Когда после изменения состояния одного объекта требуется что-то сделать в других, но вы не знаете наперёд,
 * какие именно объекты должны отреагировать.
 * <ul>Описанная проблема может возникнуть при разработке библиотек пользовательского интерфейса, когда вам надо дать
 * возможность сторонним классам реагировать на клики по кнопкам.</ul>
 * <ul>Паттерн Наблюдатель позволяет любому объекту с интерфейсом подписчика зарегистрироваться на получение оповещений
 * о событиях, происходящих в объектах-издателях.</ul>
 * <p>* Когда одни объекты должны наблюдать за другими, но только в определённых случаях.
 * <ul>Издатели ведут динамические списки. Все наблюдатели могут подписываться или отписываться от получения оповещений
 * прямо во время выполнения программы.</ul>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Шаги реализации</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>1. Разбейте вашу функциональность на две части: независимое ядро и опциональные зависимые части. Независимое
 * ядро станет издателем. Зависимые части станут подписчиками.
 * <p>2. Создайте интерфейс подписчиков. Обычно в нём достаточно определить единственный метод оповещения.
 * <p>3. Создайте интерфейс издателей и опишите в нём операции управления подпиской. Помните, что издатель должен
 * работать только с общим интерфейсом подписчиков.
 * <p>4. Вам нужно решить, куда поместить код ведения подписки, ведь он обычно бывает одинаков для всех типов издателей.
 * Самый очевидный способ — вынести этот код в промежуточный абстрактный класс, от которого будут наследоваться все
 * издатели.
 * <ul>Но если вы интегрируете паттерн в существующие классы, то создать новый базовый класс может быть затруднительно.
 * В этом случае вы можете поместить логику подписки во вспомогательный объект и делегировать ему работу из
 * издателей.</ul>
 * <p>5. Создайте классы конкретных издателей. Реализуйте их так, чтобы после каждого изменения состояния они
 * отправляли оповещения всем своим подписчикам.
 * <p>6. Реализуйте метод оповещения в конкретных подписчиках. Не забудьте предусмотреть параметры, через которые
 * издатель мог бы отправлять какие-то данные, связанные с происшедшим событием.
 * <ul>Возможен и другой вариант, когда подписчик, получив оповещение, сам возьмёт из объекта издателя нужные данные.
 * Но в этом случае вы будете вынуждены привязать класс подписчика к конкретному классу издателя.</ul>
 * <p>7. Клиент должен создавать необходимое количество объектов подписчиков и подписывать их у издателей.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Преимущества и недостатки</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>"+": издатели не зависят от конкретных классов подписчиков и наоборот.
 * <p>"+": вы можете подписывать и отписывать получателей на лету.
 * <p>"+": реализует принцип открытости/закрытости.
 * <p>"-": подписчики оповещаются в случайном порядке.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Отношения с другими паттернами</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <ul>
 * <li>
 * <strong>Цепочка обязанностей</strong>, <strong>Команда</strong>, <strong>Посредник</strong> и
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
 * <li>
 * Разница между <strong>Посредником</strong> и <strong>Наблюдателем</strong> не всегда очевидна. Чаще всего они
 * выступают как конкуренты, но иногда могут работать вместе.
 * </li>
 * <ul><i>Цель Посредника</i> — убрать обоюдные зависимости между компонентами системы. Вместо этого они становятся зависимыми
 * от самого посредника. С другой стороны, цель <i>Наблюдателя</i> — обеспечить динамическую одностороннюю связь, в которой
 * одни объекты косвенно зависят от других.</ul>
 * <ul>Довольно популярна реализация <i>Посредника</i> при помощи <i>Наблюдателя</i>. При этом объект посредника будет выступать
 * издателем, а все остальные компоненты станут подписчиками и смогут динамически следить за событиями, происходящими
 * в посреднике. В этом случае трудно понять, чем же отличаются оба паттерна.</ul>
 * <ul>Но <i>Посредник</i> имеет и другие реализации, когда отдельные компоненты жёстко привязаны к объекту посредника.
 * Такой код вряд ли будет напоминать <i>Наблюдателя</i>, но всё же останется <i>Посредником</i>.</ul>
 * <ul>Напротив, в случае реализации посредника с помощью <i>Наблюдателя</i> представим такую программу, в которой каждый
 * компонент системы становится издателем. Компоненты могут подписываться друг на друга, в то же время не привязываясь
 * к конкретным классам. Программа будет состоять из целой сети <i>Наблюдателей</i>,
 * не имея центрального объекта-<i>Посредника</i>.</ul>
 * </ul>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Примеры Наблюдателя в стандартных библиотеках Java:</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>* <strong>java.util.Observer/java.util.Observable</strong> (редко используется в реальной жизни)
 * Все реализации <strong>java.util.EventListener</strong> (практически во всём Swing-е)
 * <p>* <strong>javax.servlet.http.HttpSessionBindingListener</strong>
 * <p>* <strong>javax.servlet.http.HttpSessionAttributeListener</strong>
 * <p>* <strong>javax.faces.event.PhaseListener</strong>
 * ---------------------------------------------------------------------------------------------------------------------
 */
package patterns.refactoring.guru.behavioral.observer;