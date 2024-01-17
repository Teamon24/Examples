/**
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><a href="https://refactoring.guru/ru/design-patterns/facade"><strong>Фасад</strong></a></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * — это структурный паттерн проектирования, который предоставляет простой интерфейс к сложной системе классов, библиотеке или фреймворку.
 *
 * <p>Поэтому бизнес-логика ваших классов не будет тесно переплетается с деталями реализации сторонних классов. Такой код просто понимать и поддерживать.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Признаки применения паттерна</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * Фасад угадывается в классе, который имеет простой интерфейс, но делегирует  основную часть работы другим классам. Чаще всего, фасады сами следят за жизненным циклом объектов сложной системы.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Применимость</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>* Когда вам нужно представить простой или урезанный интерфейс к сложной подсистеме.
 *
 * <ul>Часто подсистемы усложняются по мере развития программы. Применение большинства паттернов приводит к появлению меньших классов, но в бóльшем количестве. Такую подсистему проще повторно использовать, настраивая её каждый раз под конкретные нужды, но вместе с тем, применять подсистему без настройки становится труднее. Фасад предлагает определённый вид системы по умолчанию, устраивающий большинство клиентов.</ul>
 *
 * <p>* Когда вы хотите разложить подсистему на отдельные слои.
 *
 * <ul>Используйте фасады для определения точек входа на каждый уровень подсистемы. Если подсистемы зависят друг от друга, то зависимость можно упростить, разрешив подсистемам обмениваться информацией только через фасады.</ul>
 *
 * <ul>Например, возьмём ту же сложную систему видеоконвертации. Вы хотите разбить её на слои работы с аудио и видео. Для каждой из этих частей можно попытаться создать фасад и заставить классы аудио и видео обработки общаться друг с другом через эти фасады, а не напрямую.</ul>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Шаги реализации</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * Определите, можно ли создать более простой интерфейс, чем тот, который предоставляет сложная подсистема. Вы на правильном пути, если этот интерфейс избавит клиента от необходимости знать о подробностях подсистемы.
 *
 * Создайте класс фасада, реализующий этот интерфейс. Он должен переадресовывать вызовы клиента нужным объектам подсистемы. Фасад должен будет позаботиться о том, чтобы правильно инициализировать объекты подсистемы.
 *
 * Вы получите максимум пользы, если клиент будет работать только с фасадом. В этом случае изменения в подсистеме будут затрагивать только код фасада, а клиентский код останется рабочим.
 *
 * Если ответственность фасада начинает размываться, подумайте о введении дополнительных фасадов.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Преимущества и недостатки</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>"+": изолирует клиентов от компонентов сложной подсистемы.
 * <p>"-": фасад рискует стать божественным объектом, привязанным ко всем классам программы.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Отношения с другими паттернами</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <ul>
 * <li><a href=https://refactoring.guru/ru/design-patterns/facade><strong>Фасад</strong></a> задаёт новый интерфейс, тогда как <a href=https://refactoring.guru/ru/design-patterns/adapter><strong>Адаптер</strong></a> повторно использует старый. Адаптер оборачивает только один класс, а Фасад оборачивает целую подсистему. Кроме того, Адаптер позволяет двум существующим интерфейсам работать сообща, вместо того, чтобы задать полностью новый.</li>
 *
 * <li><a href=https://refactoring.guru/ru/design-patterns/abstract-factory><strong>Абстрактная фабрика</strong</a>> может быть использована вместо <a href=https://refactoring.guru/ru/design-patterns/facade><strong>Фасада</strong></a> для того, чтобы скрыть платформо-зависимые классы.</li>
 *
 * <li><a href=https://refactoring.guru/ru/design-patterns/flyweight><strong>Легковес</strong></a> показывает, как создавать много мелких объектов, а <a href=https://refactoring.guru/ru/design-patterns/facade><strong>Фасад</strong></a> показывает, как создать один объект, который отображает целую подсистему.</li>
 *
 * <li><strong>Посредник</strong> и <a href=https://refactoring.guru/ru/design-patterns/facade><strong>Фасад</strong></a> похожи тем, что пытаются организовать работу множества существующих классов.</li>
 *
 * <ul>Фасад создаёт упрощённый интерфейс к подсистеме, не внося в неё никакой добавочной функциональности. Сама подсистема не знает о существовании Фасада. Классы подсистемы общаются друг с другом напрямую.</ul>
 * <ul>Посредник централизует общение между компонентами системы. Компоненты системы знают только о существовании Посредника, у них нет прямого доступа к другим компонентам.</ul>
 * <li><a href=https://refactoring.guru/ru/design-patterns/facade><strong>Фасад</strong></a> можно сделать <a href=https://refactoring.guru/ru/design-patterns/singleton><strong>Одиночкой</strong></a>, так как обычно нужен только один объект-фасад.</li>
 *
 * <li><a href=https://refactoring.guru/ru/design-patterns/facade><strong>Фасад</strong></a> похож на <a href=https://refactoring.guru/ru/design-patterns/proxy><strong>Заместитель</strong></a> тем, что замещает сложную подсистему и может сам её инициализировать. Но в отличие от Фасада, Заместитель имеет тот же интерфейс, что его служебный объект, благодаря чему их можно взаимозаменять.</li>
 * </ul>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Примеры Фасадов в стандартных библиотеках Java:</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 *<p>*<strong>javax.faces.context.FacesContext</strong></p> использует «под капотом» классы <strong>LifeCycle</strong>, <strong>ViewHandler</strong>, <strong>NavigationHandler</strong> и многие другие, но клиенты об этом даже не знают (что не мешает заменить эти классы другими с помощью инъекций).
 *
 * <p>*<strong>javax.faces.context.ExternalContext</strong> использует внутри классы <strong>ServletContext</strong>, <strong>HttpSession</strong>, <strong>HttpServletRequest</strong>, <strong>HttpServletResponse</strong>, и так далее.
 */
package architecture.patterns.basic.refactoring.guru.structural.facade;