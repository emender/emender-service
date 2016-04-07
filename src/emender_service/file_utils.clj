(ns emender-service.file-utils)

(def temporary-name-prefix
    "emender-service-")

(defn print-slurp-exception
    "Print warning message to the standard output."
    ([filename]
        (println "Warning: cannot read content of the following file: " filename))
    ([directory filename]
        (println "Warning: cannot read content of the following file: " directory "/" filename)))

(defn slurp-
    "Alternative (slurp) implementation that does not throw an exception, but returns nil instead."
    ([filename]
        (try
            (slurp filename)
            (catch Exception e (print-slurp-exception filename))))
    ([directory filename]
        (try
            (slurp (str directory "/" filename))
            (catch Exception e (print-slurp-exception directory filename)))))

(defn silent-slurp
    "Alternative (slurp) implementation that does not throw an exception, but returns nil instead."
    ([filename]
        (try
            (slurp filename)
            (catch Exception e)))
    ([directory filename]
        (try
            (slurp (str directory "/" filename))
            (catch Exception e))))

(defn new-file
    "Just a shortcut for (new java.io.File filename)."
    ( [filename]
    (new java.io.File filename))
    ( [path filename]
    (new java.io.File path filename)))

(defn make-temporary-log-file-name
    []
    (let [basedir-name (new java.io.File (System/getProperty "java.io.tmpdir"))
          base-name    temporary-name-prefix
          temp-file    (java.io.File/createTempFile base-name ".log" basedir-name)]
          (.getAbsolutePath temp-file)))

(defn make-temporary-directory
    "Make temporary directory that would reside in /tmp (at least on Linux)."
    []
    (let [basedir-name (System/getProperty "java.io.tmpdir")
          base-name    (str temporary-name-prefix (System/currentTimeMillis))
          temp-dir     (new java.io.File basedir-name base-name)]
        (.mkdir temp-dir)
        temp-dir))

(defn move-file
    "Move or rename one file. On the same filesystem the rename should be atomic."
    [filename1 filename2]
    (let [file1 (new java.io.File filename1)
          file2 (new java.io.File filename2)]
          (.renameTo file1 file2)))

(defn mv-file
    "Move or rename one file. On the same filesystem the rename should be atomic."
    [filename1 filename2]
    (let [file1 (new java.io.File filename1)
          file2 (new java.io.File filename2)]
          (.renameTo file1 file2)))

