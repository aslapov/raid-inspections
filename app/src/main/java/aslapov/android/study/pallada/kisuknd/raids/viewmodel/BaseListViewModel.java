package aslapov.android.study.pallada.kisuknd.raids.viewmodel;

import java.util.List;

import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;

// Базовая компонента ViewModel для списочных представлений
public interface BaseListViewModel extends BaseViewModel {

	/**
	 * Запрос списка осмотров ТС
	 */
	void getRaids();

	/**
	 * Отображение ошибки
	 * @return возвращает текст ошибки
	 */
	String getShowError();

	/**
	 * Получение списка осмотров ТС
	 */
	List<RaidWithInspectors> getRaidList();

	/**
	 * Получение позиции выбранного элемента списка
	 * @return позиция выбранного элемента списка
	 */
	int getSelectedItemPosition();

	/**
	 * Установка  позиции выбранного элемента списка
	 * @param value позиция выбранного элемента списка
	 */
	void setSelectedItemPosition(int value);

	/**
	 * Наступило ли событие изменения количества осмотров ТС в списке.
	 * Нужно для логичной работы представления: например, если количество изменилось,
	 * необходимо для 2хпанельного представления показать следующий фрагмент осмотра ТС
	 * и снять выделение выбранного элемента списка.
	 * Если количество не изменилось, выбрать первый или ранее выбранный элемент списка
	 * @return количество осмотров ТС в списке изменилось
	 */
	boolean isRaidListSizeChanged();
}
