import { Dimensions, StyleSheet } from "react-native";

export const style = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center'
    },
    boxTop: {
        height: Dimensions.get('window').height/3,
        width: '100%',
        alignItems: 'center',
        justifyContent: 'center'
    },
    boxMid: {
        height: Dimensions.get('window').height/4,
        width: '100%',
        paddingHorizontal: 50
    },
    boxBottom: {
        height: Dimensions.get('window').height/3,
        width: '100%',
        alignItems: 'center',
        //backgroundColor: 'red'
        justifyContent: 'center'
    },
    logo: {
        width: 80,
        height: 80
    },
    title: {
        fontWeight: 'bold',
        marginTop: 40, 
        fontSize: 18
    },
    labelInput: {
        marginLeft: 5,
        marginTop: 20,
        color: 'grey'
    },
    boxInput: {
        width: "100%",
        height:40,
        borderWidth: 1,
        borderRadius: 10,
        marginTop: 10,
        flexDirection: "row",
        alignItems: 'center',
        paddingHorizontal: 10,
        backgroundColor: "lightgray",
        borderColor: "lightgray"
    },
    fieldInput:{
        height: '100%',
        width: '90%'
    },
    buttonLogin: {
        width: 200,
        height: 50,
        alignItems: 'center',
        justifyContent: 'center',
        backgroundColor: 'lightblue',
        borderRadius: 10,
        marginTop: 50
    },
    buttonText: {
        fontSize: 16,
        color: 'white',
        fontWeight: 'bold'
    },
    textBottom: {
        fontSize: 16,
        color: 'gray'
    }
});