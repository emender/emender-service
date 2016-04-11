(ns emender-service.format-date)

(def unified-date-format
    (new java.text.SimpleDateFormat "yyyy-MM-dd HH:mm:ss"))

(defn format-date
    [date]
    (.format unified-date-format date))

(defn format-current-date
    []
    (format-date (new java.util.Date)))

