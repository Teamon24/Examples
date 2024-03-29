/**
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><a href="https://refactoring.guru/ru/design-patterns/memento"><strong>Снимок</strong></a></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * — это поведенческий паттерн проектирования, который позволяет сохранять и восстанавливать прошлые состояния объектов,
 * не раскрывая подробностей их реализации.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Применимость</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>* Когда вам нужно сохранять мгновенные снимки состояния объекта (или его части), чтобы впоследствии объект можно было восстановить в том же состоянии.
 * <ul>Паттерн Снимок позволяет создавать любое количество снимков объекта и хранить их, независимо от объекта, с которого делают снимок. Снимки часто используют не только для реализации операции отмены, но и для транзакций, когда состояние объекта нужно «откатить», если операция не удалась.</ul>
 * <p>* Когда прямое получение состояния объекта раскрывает приватные детали его реализации, нарушая инкапсуляцию.
 * <ul>Паттерн предлагает изготовить снимок самому исходному объекту, поскольку ему доступны все поля, даже приватные.</ul>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Шаги реализации</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>1. Определите класс создателя, объекты которого должны создавать снимки своего состояния.
 * <p>2. Создайте класс снимка и опишите в нём все те же поля, которые имеются в оригинальном классе-создателе.
 * <p>3. Сделайте объекты снимков неизменяемыми. Они должны получать начальные значения только один раз, через свой конструктор.
 * <p>4. Если ваш язык программирования это позволяет, сделайте класс снимка вложенным в класс создателя. Если нет, извлеките из класса снимка пустой интерфейс, который будет доступен остальным объектам программы. Впоследствии вы можете добавить в этот интерфейс некоторые вспомогательные методы, дающие доступ к метаданным снимка, однако прямой доступ к данным создателя должен быть исключён.
 * <p>5. Добавьте в класс создателя метод получения снимков. Создатель должен создавать новые объекты снимков, передавая значения своих полей через конструктор.
 * <ul>Сигнатура метода должна возвращать снимки через ограниченный интерфейс, если он у вас есть. Сам класс должен работать с конкретным классом снимка.</ul>
 * <p>6. Добавьте в класс создателя метод восстановления из снимка. Что касается привязки к типам, руководствуйтесь той же логикой, что и в пункте 4.
 * <p>7. Опекуны, будь то история операций, объекты команд или нечто иное, должны знать о том, когда запрашивать снимки у создателя, где их хранить и когда восстанавливать.
 * <p>8. Связь опекунов с создателями можно перенести внутрь снимков. В этом случае каждый снимок будет привязан к своему создателю и должен будет сам восстанавливать его состояние. Но это будет работать либо если классы снимков вложены в классы создателей, либо если создатели имеют соответствующие сеттеры для установки значений своих полей.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Преимущества и недостатки</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>"+": не нарушает инкапсуляции исходного объекта.
 * <p>"+": упрощает структуру исходного объекта. Ему не нужно хранить историю версий своего состояния.
 * <p>"-": требует много памяти, если клиенты слишком часто создают снимки.
 * <p>"-": может повлечь дополнительные издержки памяти, если объекты, хранящие историю, не освобождают ресурсы, занятые устаревшими снимками.
 * <p>"-": в некоторых языках (например, PHP, Python, JavaScript) сложно гарантировать, чтобы только исходный объект имел доступ к состоянию снимка.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Отношения с другими паттернами</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <ul>
 * <li><strong>Команду</strong> и <strong>Снимок</strong> можно использовать сообща для реализации отмены операций. В этом случае объекты команд будут отвечать за выполнение действия над объектом, а снимки будут хранить резервную копию состояния этого объекта, сделанную перед самым запуском команды.</li>
 * <li><strong>Снимок</strong> можно использовать вместе с <strong>Итератором</strong>, чтобы сохранить текущее состояние обхода структуры данных и вернуться к нему в будущем, если потребуется.</li>
 * <li><strong>Снимок</strong> иногда можно заменить <strong>Прототипом</strong>, если объект, состояние которого требуется сохранять в истории, довольно простой, не имеет активных ссылок на внешние ресурсы либо их можно легко восстановить.</li>
 * </ul>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Применимость</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>Снимок на Java чаще всего реализуют с помощью сериализации. Но это не единственный, да и не самый эффективный метод сохранения состояния объектов во время выполнения программы.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Примеры Снимка в стандартных библиотеках Java</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>Все реализации <strong>java.io.Serializable</strong> могут быть использованы как аналог Снимка.
 * <p>Все реализации <strong>javax.faces.component.StateHolder</strong>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Реализации</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Реализация с пустым промежуточным интерфейсом</strong>(uml-intermediate-interface.png)</p>
 * <p>Подходит для языков, не имеющих механизма вложенных классов (например, PHP).
 * <ul><p>Структура классов паттерна Снимок (Хранитель)
 * <p>1. В этой реализации создатель работает напрямую с конкретным классом снимка, а опекун — только с его ограниченным интерфейсом.
 * <p>2. Благодаря этому достигается тот же эффект, что и в классической реализации. Создатель имеет полный доступ к снимку, а опекун — нет.</ul>
 * <p><strong>Снимки с повышенной защитой</strong>(uml-extra-safety-mementos.png)</p>
 * <p>Когда нужно полностью исключить возможность доступа к состоянию создателей и снимков.
 * <ul><p>Снимок с повышенной защитой
 * <p>1. Эта реализация разрешает иметь несколько видов создателей и снимков. Каждому классу создателей соответствует свой класс снимков. Ни создатели, ни снимки не позволяют другим объектам прочесть своё состояние.
 * <p>2. Здесь опекун ещё более жёстко ограничен в доступе к состоянию создателей и снимков. Но, с другой стороны, опекун становится независим от создателей, поскольку метод восстановления теперь находится в самих снимках.
 * <p>3. Снимки теперь связаны с теми создателями, из которых они сделаны. Они по-прежнему получают состояние через конструктор. Благодаря близкой связи между классами, снимки знают, как восстановить состояние своих создателей.</ul>
 */
package architecture.patterns.basic.refactoring.guru.behavioral.memento;