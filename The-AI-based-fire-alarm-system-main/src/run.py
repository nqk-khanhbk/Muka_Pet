import os
os.environ["PYTORCH_CUDA_ALLOC_CONF"] = "expandable_segments:True"

from ultralytics import YOLO
import torch

# Giải phóng bộ nhớ GPU trước khi bắt đầu huấn luyện
torch.cuda.empty_cache()

if __name__ == '__main__':
    # Tải mô hình YOLO từ đường dẫn đã chỉ định
    model = YOLO("E:\\hung\\prj\\models\\yolov10x.pt")

    # Huấn luyện mô hình với các cài đặt tối ưu bộ nhớ và chỉ lưu một phiên bản duy nhất
    model.train(
        data="E:\\hung\\prj\\src\\mydataset.yaml",
        epochs=40,
        device='cuda',
        batch=1,
        imgsz=512,
        half=True,
        project="E:\\hung\\prj\\results",  # Đặt thư mục lưu trữ cố định
        name="yolov10_training",           # Đặt tên thư mục cụ thể để tránh tạo nhiều thư mục
        exist_ok=True                       # Ghi đè lên thư mục đã tồn tại
    )
