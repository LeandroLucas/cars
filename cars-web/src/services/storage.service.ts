import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class StorageService {

    public saveObject(key: string, value: any): void {
        const newValue = { ...value }
        if (newValue.objectsMap) {
            newValue.objectsMap = Object.fromEntries(newValue.objectsMap)
        }
        this.save(key, JSON.stringify(newValue))
    }

    public getObject(key: string): any {
        let value = this.get(key)
        if (!value)
            return null
        return JSON.parse(value);
    }

    public save(key: string, value: string) {
        localStorage.setItem(key, value);
    }

    public get(key: string): string | null {
        return localStorage.getItem(key);
    }

    public remove(key: string): void {
        localStorage.removeItem(key);
    }

    public clear() {
        localStorage.clear()
    }

}
