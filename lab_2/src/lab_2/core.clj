(ns lab-2.core
  (:import [org.opencv.core Mat MatOfInt MatOfFloat]
           [org.opencv.imgcodecs Imgcodecs]
           [org.opencv.imgproc Imgproc]))

(def src-file "/home/s/lab_2_src.jpg")
(def dst-file "/home/s/lab_2_result.png")

(defn read-img [path]
  (Imgcodecs/imread path Imgcodecs/CV_LOAD_IMAGE_GRAYSCALE))

(defn find-hist [img]
  (let [hist (Mat.)]
    (Imgproc/calcHist
      [img] ; Should be list of images.
      (MatOfInt. (int-array [0])) ; channels
      (Mat.) ; Mask. Optional, empty matrix if no mask needed.
      hist ; result histogram
      (MatOfInt. (int-array [256])) ; hist size
      (MatOfFloat. (float-array [0 255])) ; hist ranges. Enough to set [0 255] for uniform distribution
      true) ; uniform
    hist))

(defn hist-vals [hist]
  (vec (map #(first (.get hist % 0)) (range 256))))

(defn x-distribution [hist]
  (let [h-vals (hist-vals hist)
        raw (mapv #(reduce + (subvec h-vals 0 %)) (range 256))
        maxval (inc (apply max raw))]
    (map #(/ % maxval) raw)))

; Relay formula.
(defn xdist->y [xdist min-y alpha]
  (+ min-y
     (Math/sqrt
       (* 2 
          alpha alpha
          (Math/log (/ 1.0 (- 1 xdist)))))))

; Usage:
; calculate y-map once.
; ; (def x->y (y-map x-distribution 0 1))
; then for each x you can just get (x->y x)
; for example, if you have old value x = 42 then y = (x->y 42).
;
(defn y-map [x-distribution min-y alpha]
  (mapv #(double-array [(xdist->y % min-y alpha)]) x-distribution))

(defn transform-image [img x->y]
  (for [col (range (.width img)) row (range (.height img))]
    (.put img row col (x->y (int (first (.get img row col)))))))

(defn -main
  "Do lab2:
  * read image
  * clone src to dst
  * find x to y map
  * replace each pixel with new value
  * write dst to dst_file"
  [min-y alpha]
  (let [src (Imgcodecs/imread src-file Imgcodecs/CV_LOAD_IMAGE_GRAYSCALE)
        dst (.clone src)
        x->y (y-map (x-distribution (find-hist src)) min-y alpha)]
    (doall (transform-image dst x->y))
    (Imgcodecs/imwrite dst-file dst)))
