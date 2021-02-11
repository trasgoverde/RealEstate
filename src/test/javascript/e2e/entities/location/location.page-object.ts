import { element, by, ElementFinder } from 'protractor';

export class LocationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-location div table .btn-danger'));
  title = element.all(by.css('jhi-location div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class LocationUpdatePage {
  pageTitle = element(by.id('jhi-location-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  refInput = element(by.id('field_ref'));
  provinceInput = element(by.id('field_province'));
  townInput = element(by.id('field_town'));
  cpInput = element(by.id('field_cp'));
  typeOfRoadSelect = element(by.id('field_typeOfRoad'));
  nameRoadInput = element(by.id('field_nameRoad'));
  numberInput = element(by.id('field_number'));
  apartmentInput = element(by.id('field_apartment'));
  buildingInput = element(by.id('field_building'));
  doorInput = element(by.id('field_door'));
  stairInput = element(by.id('field_stair'));
  urlgmapsInput = element(by.id('field_urlgmaps'));
  latitudeInput = element(by.id('field_latitude'));
  longitudeInput = element(by.id('field_longitude'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setRefInput(ref: string): Promise<void> {
    await this.refInput.sendKeys(ref);
  }

  async getRefInput(): Promise<string> {
    return await this.refInput.getAttribute('value');
  }

  async setProvinceInput(province: string): Promise<void> {
    await this.provinceInput.sendKeys(province);
  }

  async getProvinceInput(): Promise<string> {
    return await this.provinceInput.getAttribute('value');
  }

  async setTownInput(town: string): Promise<void> {
    await this.townInput.sendKeys(town);
  }

  async getTownInput(): Promise<string> {
    return await this.townInput.getAttribute('value');
  }

  async setCpInput(cp: string): Promise<void> {
    await this.cpInput.sendKeys(cp);
  }

  async getCpInput(): Promise<string> {
    return await this.cpInput.getAttribute('value');
  }

  async setTypeOfRoadSelect(typeOfRoad: string): Promise<void> {
    await this.typeOfRoadSelect.sendKeys(typeOfRoad);
  }

  async getTypeOfRoadSelect(): Promise<string> {
    return await this.typeOfRoadSelect.element(by.css('option:checked')).getText();
  }

  async typeOfRoadSelectLastOption(): Promise<void> {
    await this.typeOfRoadSelect.all(by.tagName('option')).last().click();
  }

  async setNameRoadInput(nameRoad: string): Promise<void> {
    await this.nameRoadInput.sendKeys(nameRoad);
  }

  async getNameRoadInput(): Promise<string> {
    return await this.nameRoadInput.getAttribute('value');
  }

  async setNumberInput(number: string): Promise<void> {
    await this.numberInput.sendKeys(number);
  }

  async getNumberInput(): Promise<string> {
    return await this.numberInput.getAttribute('value');
  }

  async setApartmentInput(apartment: string): Promise<void> {
    await this.apartmentInput.sendKeys(apartment);
  }

  async getApartmentInput(): Promise<string> {
    return await this.apartmentInput.getAttribute('value');
  }

  async setBuildingInput(building: string): Promise<void> {
    await this.buildingInput.sendKeys(building);
  }

  async getBuildingInput(): Promise<string> {
    return await this.buildingInput.getAttribute('value');
  }

  async setDoorInput(door: string): Promise<void> {
    await this.doorInput.sendKeys(door);
  }

  async getDoorInput(): Promise<string> {
    return await this.doorInput.getAttribute('value');
  }

  async setStairInput(stair: string): Promise<void> {
    await this.stairInput.sendKeys(stair);
  }

  async getStairInput(): Promise<string> {
    return await this.stairInput.getAttribute('value');
  }

  async setUrlgmapsInput(urlgmaps: string): Promise<void> {
    await this.urlgmapsInput.sendKeys(urlgmaps);
  }

  async getUrlgmapsInput(): Promise<string> {
    return await this.urlgmapsInput.getAttribute('value');
  }

  async setLatitudeInput(latitude: string): Promise<void> {
    await this.latitudeInput.sendKeys(latitude);
  }

  async getLatitudeInput(): Promise<string> {
    return await this.latitudeInput.getAttribute('value');
  }

  async setLongitudeInput(longitude: string): Promise<void> {
    await this.longitudeInput.sendKeys(longitude);
  }

  async getLongitudeInput(): Promise<string> {
    return await this.longitudeInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class LocationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-location-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-location'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
