#coding=utf-8
import requests
import sys
def upToFir():
    #打印传递过来的参数数组长度，便于校验
    print('the arg length:'+str(len(sys.argv)))
    up_url = sys.argv[1]
    app_name = sys.argv[2]
    bundle_id = sys.argv[3]
    ver_name = sys.argv[4]
    api_token = sys.argv[5]
    apk_path = sys.argv[6]
    build_number = sys.argv[7]
    change_log = sys.argv[8]
    query_data = {'type':'android','bundle_id':bundle_id,'api_token':api_token}
    iconDict = {}
    binaryDict = {}
    try:
        response = requests.post(url=up_url,data=query_data)
        json = response.json()
        iconDict = (json["cert"]["icon"])
        binaryDict = (json["cert"]["binary"])
    except Exception as e:
        print(e)
    try:
        file = {'file': open(apk_path, 'rb')}
        param = {'key':binaryDict['key'],'token':binaryDict['token'],'x:name':app_name,'x:version':ver_name,'x:build':build_number,'x:changelog':change_log}
        upload_response = requests.post(url=binaryDict['upload_url'],data=param,files=file,verify=False)
        print('success upload'+upload_response.content)
    except Exception as e:
        print'error_apk:' + e

if __name__ == '__main__':
    upToFir()


