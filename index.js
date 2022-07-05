
import { NativeModules } from 'react-native';

const { RNFaceVerify } = NativeModules;


const FaceVerify = {
    init:(config)=>{
        return new Promise((resolve,reject)=>{
            RNFaceVerify.init(config,(result)=>{
                result = JSON.parse(result)
                resolve(result)
            });
        })
    },
    startVerify:(params)=>{
        return new Promise((resolve,reject)=>{
            RNFaceVerify.startVerify(params,(result)=>{
                result = JSON.parse(result)
                resolve(result)
            });
        })
    },
    test:()=>{
        console.log("test")
    }
}

export default FaceVerify;
