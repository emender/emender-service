(ns emender-service.server-test
  (:require [clojure.test :refer :all]
            [emender-service.server :refer :all]))

(def request
    {:configuration {:api {:prefix "/v1"}}})

(deftest test-get-api-command
    (testing "Checks get-api-command-function"
        (are [x y] (= x y)
            nil            (get-api-command request nil)
            nil            (get-api-command request "/v1")
            "/job-started" (get-api-command request "/v1/job-started")
            "/job-started" (get-api-command request "/v1/job-started/")
            "/job-started" (get-api-command request "/v1/job-started/jobName")
        )))

