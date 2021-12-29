/**
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><a href=https://refactoring.guru/ru/design-patterns/composite><strong>Компоновщик</strong></a></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * — это структурный паттерн проектирования, который позволяет сгруппировать множество объектов в древовидную структуру,
 * а затем работать с ней так, как будто это единичный объект.
 * <p><strong>Применимость</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>* Когда вам нужно представить древовидную структуру объектов.
 *
 * <ul>Паттерн Компоновщик предлагает хранить в составных объектах ссылки на другие простые или составные объекты.
 * Те, в свою очередь, тоже могут хранить свои вложенные объекты и так далее. В итоге вы можете строить сложную
 * древовидную структуру данных, используя всего две основные разновидности объектов.</ul>
 *
 * <p>* Когда клиенты должны единообразно трактовать простые и составные объекты.
 *
 * <ul>Благодаря тому, что простые и составные объекты реализуют общий интерфейс, клиенту безразлично, с каким именно
 * объектом ему предстоит работать.</ul>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Шаги реализации</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>1. Убедитесь, что вашу бизнес-логику можно представить как древовидную структуру. Попытайтесь разбить её на
 * простые компоненты и контейнеры. Помните, что контейнеры могут содержать как простые компоненты,
 * так и другие вложенные контейнеры.
 * <p>2. Создайте общий интерфейс компонентов, который объединит операции контейнеров и простых компонентов дерева.
 * Интерфейс будет удачным, если вы сможете использовать его, чтобы взаимозаменять простые и составные компоненты
 * без потери смысла.
 * <p>3. Создайте класс компонентов-листьев, не имеющих дальнейших ответвлений. Имейте в виду, что программа может
 * содержать несколько таких классов.
 * <p>4. Создайте класс компонентов-контейнеров и добавьте в него массив для хранения ссылок на вложенные компоненты.
 * Этот массив должен быть способен содержать как простые, так и составные компоненты, поэтому убедитесь,
 * что он объявлен с типом интерфейса компонентов.
 * <ul>Реализуйте в контейнере методы интерфейса компонентов, помня о том, что контейнеры должны делегировать основную
 * работу своим дочерним компонентам.</ul>
 * <p>5. Добавьте операции добавления и удаления дочерних компонентов в класс контейнеров.
 * <ul>Имейте в виду, что методы добавления/удаления дочерних компонентов можно поместить и в интерфейс компонентов.
 * Да, это нарушит принцип разделения интерфейса, так как реализации методов будут пустыми в компонентах-листьях.
 * Но зато все компоненты дерева станут действительно одинаковыми для клиента.</ul>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Преимущества и недостатки</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>"+": упрощает архитектуру клиента при работе со сложным деревом компонентов.
 * <p>"+": облегчает добавление новых видов компонентов.
 * <p>"-": создаёт слишком общий дизайн классов.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Отношения с другими паттернами</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <ul>
 * <li><a href=https://refactoring.guru/ru/design-patterns/builder><strong>Строитель</strong></a> позволяет пошагово сооружать дерево <a href=https://refactoring.guru/ru/design-patterns/composite><strong>Компоновщика</strong></a>.</li>
 * <li><a href=https://refactoring.guru/ru/design-patterns/chain-of-responsibility><strong>Цепочку</strong></a> обязанностей часто используют вместе с <a href=https://refactoring.guru/ru/design-patterns/composite><strong>Компоновщиком</strong></a>. В этом случае запрос передаётся от дочерних
 * компонентов к их родителям.</li>
 * <li>Вы можете обходить дерево <a href=https://refactoring.guru/ru/design-patterns/composite><strong>Компоновщика</strong></a>, используя <a href=https://refactoring.guru/ru/design-patterns/iterator><strong>Итератор</strong></a>.</li>
 * <li>Вы можете выполнить какое-то действие над всем деревом <a href=https://refactoring.guru/ru/design-patterns/composite><strong>Компоновщика</strong></a> при помощи <a href=https://refactoring.guru/ru/design-patterns/visitor><strong>Посетителя</strong></a>.</li>
 * <li><a href=https://refactoring.guru/ru/design-patterns/composite><strong>Компоновщик</strong></a> часто совмещают с <a href=https://refactoring.guru/ru/design-patterns/flyweight><strong>Легковесом</strong></a>, чтобы реализовать общие ветки дерева и сэкономить при этом память.</li>
 * <li><a href=https://refactoring.guru/ru/design-patterns/composite><strong>Компоновщик</strong></a> и <a href=https://refactoring.guru/ru/design-patterns/decorator><strong>Декоратор</strong></a> имеют похожие структуры классов из-за того, что оба построены на рекурсивной вложенности.
 * Она позволяет связать в одну структуру бесконечное количество объектов.</li>
 * <ul>Декоратор оборачивает только один объект, а узел Компоновщика может иметь много детей. Декоратор добавляет
 * вложенному объекту новую функциональность, а Компоновщик не добавляет ничего нового, но «суммирует» результаты всех своих детей.</ul>
 * <ul>Но они могут и сотрудничать: Компоновщик может использовать Декоратор, чтобы переопределить функции отдельных
 * частей дерева компонентов.</ul>
 * <li>Архитектура, построенная на <a href=https://refactoring.guru/ru/design-patterns/composite><strong>Компоновщиках</strong></a> и <a href=https://refactoring.guru/ru/design-patterns/decorator><strong>Декораторах</strong></a>, часто может быть улучшена за счёт внедрения <a href=https://refactoring.guru/ru/design-patterns/prototype><strong>Прототипа</strong></a>.
 * Он позволяет клонировать сложные структуры объектов, а не собирать их заново.</li>
 * </ul>
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Примеры Компоновщиков в стандартных библиотеках Java:</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>*<strong>java.awt.Container#add(Component)</strong>(обычно применим для компонентов Swing)
 * <p>*<strong>javax.faces.component.UIComponent#getChildren()</strong> (обычно применим для JSF UI)
 * Признаки применения паттерна: Если
 *
 */
package patterns.refactoring.guru.structural.composite;