package eu.tkacas.smartalert.viewmodel.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.tkacas.smartalert.cloud.getStatisticsPerYear
import eu.tkacas.smartalert.models.CriticalWeatherPhenomenon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnalyticsViewModel: ViewModel() {
    private var _data = mutableMapOf<String, Any>()
    private var _years = MutableLiveData<List<String>>(listOf())
    private var _months = MutableLiveData<List<String>>(listOf())

    private var _selectedYear: String? = null
    private var _selectedMonth: String? = null


    private var _earthquakeCount = MutableLiveData<Int>(0)
    private var _floodCount = MutableLiveData<Int>(0)
    private var _wildfireCount = MutableLiveData<Int>(0)
    private var _riverFloodCount = MutableLiveData<Int>(0)
    private var _heatwaveCount = MutableLiveData<Int>(0)
    private var _snowstormCount = MutableLiveData<Int>(0)
    private var _stormCount = MutableLiveData<Int>(0)

    var selectedYear: String?
        get() = _selectedYear
        set(value) {
            _selectedYear = value
        }

    var selectedMonth: String?
        get() = _selectedMonth
        set(value) {
            _selectedMonth = value
        }

    var years: MutableLiveData<List<String>>
        get() = _years
        set(value) {
            _years = value
        }

    var months: MutableLiveData<List<String>>
        get() = _months
        set(value) {
            _months = value
        }

    var earthquakeCount: MutableLiveData<Int>
        get() = _earthquakeCount
        set(value) {
            _earthquakeCount = value
        }

    var floodCount: MutableLiveData<Int>
        get() = _floodCount
        set(value) {
            _floodCount = value
        }

    var wildfireCount: MutableLiveData<Int>
        get() = _wildfireCount
        set(value) {
            _wildfireCount = value
        }

    var riverFloodCount: MutableLiveData<Int>
        get() = _riverFloodCount
        set(value) {
            _riverFloodCount = value
        }

    var heatwaveCount: MutableLiveData<Int>
        get() = _heatwaveCount
        set(value) {
            _heatwaveCount = value
        }

    var snowstormCount: MutableLiveData<Int>
        get() = _snowstormCount
        set(value) {
            _snowstormCount = value
        }

    var stormCount: MutableLiveData<Int>
        get() {
            return _stormCount
        }
        set(value) {
            _stormCount = value
        }

    init {
        fetchData()
    }

    private fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            getStatisticsPerYear { success, dataRetrieved, error ->
                if (success) {
                    _data = dataRetrieved?.toMutableMap() ?: mutableMapOf()
                    _years.postValue(fetchYears())
                    _months.postValue(fetchMonths())
                } else {
                    println("Error fetching data: $error")
                }
            }
        }
    }

    private fun fetchYears(): List<String> {
        val years = mutableListOf<String>()
        _data.keys.forEach { key ->
            years.add(key)
        }
        return years
    }

    private fun fetchMonths(): List<String> {
        val months = mutableSetOf<String>()
        _data.values.forEach { value ->
            if (value is Map<*, *>) {
                val sumPerMonth = value["sumPerMonth"] as? Map<String, Any>
                sumPerMonth?.keys?.forEach { month ->
                    months.add(month)
                }
            }
        }
        return months.toList()
    }

fun fetchStatisticsPerMonth() {
        val year = _selectedYear
        val month = _selectedMonth

        val data = _data[year] as? Map<*, *>
        if (month == "ALL_MONTHS") {
            val sumOfReports = data?.get("sumOfReports") as? Map<*, *>
            _earthquakeCount.postValue((sumOfReports?.get(CriticalWeatherPhenomenon.EARTHQUAKE.toString()) as? Long)?.toInt() ?: 0)
            _floodCount.postValue((sumOfReports?.get(CriticalWeatherPhenomenon.FLOOD.toString()) as? Long)?.toInt() ?: 0)
            _wildfireCount.postValue((sumOfReports?.get(CriticalWeatherPhenomenon.WILDFIRE.toString()) as? Long)?.toInt() ?: 0)
            _riverFloodCount.postValue((sumOfReports?.get(CriticalWeatherPhenomenon.RIVER_FLOOD.toString()) as? Long)?.toInt() ?: 0)
            _heatwaveCount.postValue((sumOfReports?.get(CriticalWeatherPhenomenon.HEATWAVE.toString()) as? Long)?.toInt() ?: 0)
            _snowstormCount.postValue((sumOfReports?.get(CriticalWeatherPhenomenon.SNOWSTORM.toString()) as? Long)?.toInt() ?: 0)
            _stormCount.postValue((sumOfReports?.get(CriticalWeatherPhenomenon.STORM.toString()) as? Long)?.toInt() ?: 0)
        } else {
            val sumPerMonth = data?.get("sumPerMonth") as? Map<*, *>
            val monthData = sumPerMonth?.get(month) as? Map<*, *>
            _earthquakeCount.postValue((monthData?.get(CriticalWeatherPhenomenon.EARTHQUAKE.toString()) as? Long)?.toInt() ?: 0)
            _floodCount.postValue((monthData?.get(CriticalWeatherPhenomenon.FLOOD.toString()) as? Long)?.toInt() ?: 0)
            _wildfireCount.postValue((monthData?.get(CriticalWeatherPhenomenon.WILDFIRE.toString()) as? Long)?.toInt() ?: 0)
            _riverFloodCount.postValue((monthData?.get(CriticalWeatherPhenomenon.RIVER_FLOOD.toString()) as? Long)?.toInt() ?: 0)
            _heatwaveCount.postValue((monthData?.get(CriticalWeatherPhenomenon.HEATWAVE.toString()) as? Long)?.toInt() ?: 0)
            _snowstormCount.postValue((monthData?.get(CriticalWeatherPhenomenon.SNOWSTORM.toString()) as? Long)?.toInt() ?: 0)
            _stormCount.postValue((monthData?.get(CriticalWeatherPhenomenon.STORM.toString()) as? Long)?.toInt() ?: 0)
        }
    }

}