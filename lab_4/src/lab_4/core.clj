(ns lab-4.core
  (:import [org.opencv.core Mat MatOfInt MatOfFloat]
           [org.opencv.imgcodecs Imgcodecs]
           [org.opencv.imgproc Imgproc]))

(def src-file "/home/s/lab_4_src.jpg")
(def dst-file "/home/s/lab_4_result.png")

(defn read-img [path]
  (Imgcodecs/imread path Imgcodecs/CV_LOAD_IMAGE_GRAYSCALE))

; max filter
(defn transform-pixel [img col row mask-size]
  (let [submat (for [x (range (- col mask-size) (+ col mask-size))
                     y (range (- row mask-size) (+ row mask-size))]
                     (or (first (.get img x y)) 0))]
    (apply max submat)))

(defn transform-image [src dst mask-size]
  (for [row (range (.width dst)) col (range (.height dst))]
    (.put dst col row (double-array [(transform-pixel src col row mask-size)]))))

(defn -main
  "Do lab4:
  * read image
  * clone src to dst
  * apply max mask filter to every pixel
  * write dst to dst_file"
  [mask-size]
  (let [src (Imgcodecs/imread src-file Imgcodecs/CV_LOAD_IMAGE_GRAYSCALE)
        dst (.clone src)]
    (doall (transform-image src dst mask-size))
    (Imgcodecs/imwrite dst-file dst)))
