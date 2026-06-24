import { configureStore } from "@reduxjs/toolkit";

/**
 * Redux Store 配置（轻量化选择）
 * <p>当前为空配置，后续可按需添加 slice reducer</p>
 */
const store = configureStore({
  reducer: {
    // 后续在此添加各个 slice reducer
    // user: userReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;
