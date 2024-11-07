Tình huống trong trường hợp sử dụng multithreading
- Có 10 users cùng request tới 1 API
- Request của user nào xong trước thì response data cho user đó
- Ex: user 1 call API A, và thực hiện mất 10 phút
  user 2 call API A cùng lúc và thực hiện mất 3 phút
  user 3 call API A cùng lúc và thực hiện mất 5 phút
  -> Kết quả nhận được là User 2 nhận được dữ liệu trước, sau đó là user 3 và cuối cùng là user 1
