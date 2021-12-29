/**
 * Java Memory Model.
 * Описывает то, как потоки должны взаимодейстовать через общую память.
 *
 * Основные проблемы:
 * - кэширование значнией в многопроцессорных средах
 * - изменение порядка операций для оптимизации.
 *
 * Инструменты для решения:
 * - final - не изменять переменную
 * - volatile - не кэшировать, всегда считывать из общей памяти (даже в случае многоядерности)
 * - synchronized - отметить участок кода доступный одному потоку.
 */
package thread;