let appInstance = getApp();
const { windowWidth, domain, prefix, types } = appInstance.globalData;
Page({
  data: {
    prefix,
    windowWidth,
    pictures: [],
    remarks: '',
    description: ''
  },

  chooseImages: function (e) {
    const id = e.currentTarget.id;
    let count = e.currentTarget.dataset.count
    const length = this.data[id].length;
    count = count - length;
    appInstance.chooseImages({count,url:'product/uploadPicture'}).then((data) => {
      console.log(data)
      this.setData({
        [`${id}[${length}]`]: data
      })
    })
  },

  chooseImage: function (e) {
    const id = e.currentTarget.id;
    appInstance.chooseImage({url:'product/uploadPicture'}).then((data) => {
      this.setData({
        [`${id}`]: data
      })
    })
  },

  deletePictures: function (e) {
    const id = e.currentTarget.id;
    const index = e.currentTarget.dataset.index;
    const files = this.data[id];
    files.splice(index, 1);
    this.setData({
      [id]: files
    })
  },

  deletePicture: function (e) {
    const id = e.currentTarget.id;
    this.setData({
      [id]: null
    })
  },

  clearPictues: function (e) {
    const id = e.currentTarget.id;
    this.setData({
      [id]: []
    })
  },

  textareaInput: function (e) {
    const id = e.currentTarget.id;
    const value = e.detail.value;
    this.setData({
      [id]: value
    })
  },

  bindInput: function (e) {
    const id = e.currentTarget.id;
    let count = e.currentTarget.dataset.count;
    const value = e.detail.value;
    if (value.length == count) {
      this.setData({
        [id]: value
      })
    }
  },
  createProduct: function (e) {
    const { remarks, description, pictures, id } = this.data;
    const product = { ...e.detail.value, remarks, description, pictures, id }
    appInstance.request({ url: `${domain}/product`, data: product, method: "POST" }).then((data) => {
      this.setData({
        ...data.content
      })
      let pages = getCurrentPages();
      let prevPage = pages[pages.length - 2];
      prevPage.setData({
        products: [data.content, ...prevPage.data.products]
      });
      wx.navigateBack({
        delta: 1
      })
    })
  },
  resetForm: function (e) {
    this.setData({
      pictures: [],
      vid: null,
      remarks: '',
      description: ''
    })
  },
})