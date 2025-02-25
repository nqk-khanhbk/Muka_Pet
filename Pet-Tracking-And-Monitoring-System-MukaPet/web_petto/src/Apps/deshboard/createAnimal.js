import { PlusOutlined } from "@ant-design/icons";
import { Modal } from "antd";
import { useState } from "react";
import "./createAnimal.css";
import { getCookie } from "../../helpers/cookies";
import { message } from "antd";

function CreateAnimal(props) {
  const { onReload } = props;
  const [messageApi, contextHolder] = message.useMessage();
  const [modalOpen, setModalOpen] = useState(false);
  const [data, setData] = useState({
    petName: "",
    species: "",
    breed: "",
    age: "",
    weight: "",
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    const token = getCookie("accessToken");

    const formData = new FormData();
    Object.keys(data).forEach((key) => {
      formData.append(key, data[key]);
    });

    const fileInput = document.getElementById("petPicture");
    if (fileInput.files[0]) {
      formData.append("petPicture", fileInput.files[0]);
    } else {
      console.warn("No file selected for picture");
    }

    fetch(`http://localhost:8087/myhome/pet/add`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
        Accept: "application/json",
      },
      body: formData,
    })
      .then((res) => res.json())
      .then((data) => {
        if (data) {
          setModalOpen(false);
          onReload();
          messageApi.open({
            type: "success",
            content: "Bạn đã thêm một thú cưng mới!",
            duration: 5,
          });
        } else {
          messageApi.open({
            type: "warning",
            content: "Vui lòng tạo lại!",
            duration: 4,
          });
        }
      });
  };

  const handleChange = (e) => {
    const { name, value, files } = e.target;

    if (name === "petPicture") {
      // Không lưu file trong state
      return;
    }

    setData({
      ...data,
      [name]: value,
    });
  };

  return (
    <>
      {contextHolder}
      <div className="animal_add" onClick={() => setModalOpen(true)}>
        <div>
          <PlusOutlined />
        </div>
      </div>

      <Modal
        open={modalOpen}
        onOk={() => setModalOpen(false)}
        onCancel={() => setModalOpen(false)}
        footer={null}
      >
        <form className="product-form" onSubmit={handleSubmit}>
          <table>
            <thead>
              <tr>
                <td>Tên động vật</td>
                <td>
                  <input type="text" name="petName" onChange={handleChange} />
                </td>
              </tr>
              <tr>
                <td>Giống động vật</td>
                <td>
                  <input type="text" name="breed" onChange={handleChange} />
                </td>
              </tr>
              <tr>
                <td>Loài động vật</td>
                <td>
                  <input type="text" name="species" onChange={handleChange} />
                </td>
              </tr>
              <tr>
                <td>Tuổi</td>
                <td>
                  <input type="number" name="age" onChange={handleChange} />
                </td>
              </tr>
              <tr>
                <td>Cân nặng</td>
                <td>
                  <input
                    type="number"
                    name="weight"
                    step="0.1"
                    onChange={handleChange}
                  />
                </td>
              </tr>
              <tr>
                <td>Ảnh động vật</td>
                <td>
                  <input type="file" name="petPicture" id="petPicture" />
                </td>
              </tr>
            </thead>
          </table>
          <div className="button_create">
            <input type="submit" value="Tạo mới" className="btn btn-submit" />
          </div>
        </form>
      </Modal>
    </>
  );
}

export default CreateAnimal;
