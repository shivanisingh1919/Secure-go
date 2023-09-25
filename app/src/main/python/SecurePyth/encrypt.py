import cv2
import numpy 
import os
import sys
import time
import math


def encrypt(background,sketch,key):
    #get data out of source image
    data_to_be_saved=key+IMG2Dat(sketch)
    temp=""
    if(imageIsPng(background)==False):
        background=toPng(background)
        temp=background
    enc=encode(background,data_to_be_saved)
    if(temp!=""):
        os.remove(temp)
    if((str(type(enc))=="<class 'numpy.ndarray'>")==False):
        return "error"
    enc.dtype=numpy.uint8
    file_name=getPath(sketch)+"enc_"+str(math.floor(time.time()))+"."+getExtension(background)
    cv2.imwrite(file_name,enc)
    return file_name

def imageIsPng(fname):
    return getExtension(fname)=="png"

def toPng(imageFile):
    tempimg=cv2.imread(imageFile)
    filename=imageFile[:-len(getExtension(imageFile))]+"png"
    cv2.imwrite(filename,tempimg)
    return filename
    

def IMG2Dat(fnm):
    img=cv2.imread(fnm)
    gr=cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
    cny=cv2.Canny(gr,50,255)
    im2, cont, hierarchy = cv2.findContours(cny, cv2.RETR_TREE, cv2.CHAIN_APPROX_NONE)#cv2==3.4.2.16
    #cont,hr=cv2.findContours(cny,cv2.RETR_TREE,cv2.CHAIN_APPROX_NONE) #cv2==4.2.0
    arr=""
    i=0
    for a in cont:
        for b in a:
            for c in b:
                for d in c:
                    arr=arr+str(d)+","
        arr=arr+"0,0,"
    return arr

def encode(image_name, secret_data):
    # read the image
    image = cv2.imread(image_name)
    # maximum bytes to encode
    n_bytes = image.shape[0] * image.shape[1] * 3 // 8
    #print("[*] Maximum bytes to encode:", n_bytes)
    if len(secret_data) > n_bytes:
        return "error"
        #raise ValueError("[!] Insufficient bytes, need bigger image or less data.")
    #print("[*] Encoding data...")
    # add stopping criteria
    secret_data += "====="
    data_index = 0
    # convert data to binary
    binary_secret_data = to_bin(secret_data)
    if(binary_secret_data=="error"):
        return "error"
    # size of data to hide
    data_len = len(binary_secret_data)
    for row in image:
        for pixel in row:
            # convert RGB values to binary format
            r, g, b = to_bin(pixel)
            
            # modify the least significant bit only if there is still data to store
            if data_index < data_len:
                # least significant red pixel bit
                pixel[0] = int(r[:-1] + binary_secret_data[data_index], 2)
                data_index += 1
            if data_index < data_len:
                # least significant green pixel bit
                pixel[1] = int(g[:-1] + binary_secret_data[data_index], 2)
                data_index += 1
            if data_index < data_len:
                # least significant blue pixel bit
                pixel[2] = int(b[:-1] + binary_secret_data[data_index], 2)
                data_index += 1
            # if data is encoded, just break out of the loop
            if data_index >= data_len:
                break
    return image

def to_bin(data):
    #Convert `data` to binary format as string
    if isinstance(data, str):
        return ''.join([ format(ord(i), "08b") for i in data ])
    elif isinstance(data, bytes) or isinstance(data, numpy.ndarray):
        return [ format(i, "08b") for i in data ]
    elif isinstance(data, int) or isinstance(data, numpy.uint8):
        return format(data, "08b")
    else:
        return "error"

def getPath(source):
    line=""
    path=""
    for character in source:
        line=line+character
        if(character=="\\" or character=="/"):
            path=path+line
            line=""
    return path
def getExtension(source):
    ext=""
    for letter in source:
        ext=ext+letter
        if letter==".":
            ext=""
    return ext




#testing purpose
#print(encrypt(sys.argv[1],sys.argv[2],sys.argv[3]))