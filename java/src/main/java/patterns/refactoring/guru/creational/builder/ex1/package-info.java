/**
 * ============================================================================
 * Пошаговое производство автомобилей
 * ============================================================================
 * <p>В этом примере Строитель позволяет пошагово конструировать различные конфигурации автомобилей.
 * <p>Кроме того, здесь показано как с помощью Строителя может создавать другой продукт на основе тех же шагов строительства. Для этого мы имеем два конкретных строителя — CarBuilder и CarManualBuilder.
 * <p>Порядок строительства продуктов заключён в Директоре. Он знает какие шаги строителей нужно вызвать, чтобы получить ту или иную конфигурацию продукта. Он работает со строителями только через общий интерфейс, что позволяет взаимозаменять объекты строителей для получения разных продуктов.
 * <ul><p>Автомобиль — это сложный объект, который может быть сконфигурирован сотней разных способов. Вместо того, чтобы настраивать автомобиль через конструктор, мы вынесем его сборку в отдельный класс-строитель, предусмотрев методы для конфигурации всех частей автомобиля.</ul>
 * <ul><p>Клиент может собирать автомобили, работая со строителем напрямую. Но, с другой стороны, он может поручить это дело директору. Это объект, который знает, какие шаги строителя нужно вызвать, чтобы получить несколько самых популярных конфигураций автомобилей.</ul>
 * <ul><p>Но к каждому автомобилю нужно ещё и руководство, совпадающее с его конфигурацией. Для этого мы создадим ещё один класс строителя, который вместо конструирования автомобиля, будет печатать страницы руководства к той детали, которую мы встраиваем в продукт. Теперь, пропустив оба типа строителей через одни и те же шаги, мы получим автомобиль и подходящее к нему руководство пользователя.</ul>
 * <ul><p>Очевидно, что бумажное руководство и железный автомобиль — это две разных вещи, не имеющих ничего общего. По этой причине мы должны получать результат напрямую от строителей, а не от директора. Иначе нам пришлось бы жёстко привязать директора к конкретным классам автомобилей и руководств.</ul>
 */
package patterns.refactoring.guru.creational.builder.ex1;