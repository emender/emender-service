;
;  (C) Copyright 2016  Pavel Tisnovsky
;
;  All rights reserved. This program and the accompanying materials
;  are made available under the terms of the Eclipse Public License v1.0
;  which accompanies this distribution, and is available at
;  http://www.eclipse.org/legal/epl-v10.html
;
;  Contributors:
;      Pavel Tisnovsky
;

(ns emender-service.middleware)

(defn inject-configuration
    "Inject configuration structure into the request parameter."
    [handler configuration]
    (fn [request]
        (handler (assoc request :configuration configuration))))

