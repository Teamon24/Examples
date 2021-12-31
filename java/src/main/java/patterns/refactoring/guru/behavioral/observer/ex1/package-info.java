/**
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Подписка и оповещения</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <ul>В этом примере Наблюдатель используется для передачи событий между объектами текстового редактора. Всякий раз когда объект редактора меняет своё состояние, он оповещает своих наблюдателей. Объекты EmailNotificationListener и LogOpenListener следят за этими уведомлениями и выполняют полезную работу в ответ.</ul>
 *
 * <ul>Классы подписчиков не связаны с классом редактора и могут быть повторно использованы в других приложениях если потребуется. Класс Editor зависит только от общего интерфейса подписчиков. Это позволяет добавлять новые типы подписчиков не меняя существующего кода редактора.</ul>
 */
package patterns.refactoring.guru.behavioral.observer.ex1;