import { element, by, ElementFinder } from 'protractor';

export class PropertyComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-property div table .btn-danger'));
  title = element.all(by.css('jhi-property div h2#page-heading span')).first();
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

export class PropertyUpdatePage {
  pageTitle = element(by.id('jhi-property-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  priceInput = element(by.id('field_price'));
  descriptionInput = element(by.id('field_description'));
  buildingTypeSelect = element(by.id('field_buildingType'));
  serviceTypeSelect = element(by.id('field_serviceType'));
  refInput = element(by.id('field_ref'));
  visibleInput = element(by.id('field_visible'));
  soldInput = element(by.id('field_sold'));
  terraceInput = element(by.id('field_terrace'));
  m2Input = element(by.id('field_m2'));
  numberBedroomInput = element(by.id('field_numberBedroom'));
  elevatorInput = element(by.id('field_elevator'));
  furnishedInput = element(by.id('field_furnished'));
  poolInput = element(by.id('field_pool'));
  garageInput = element(by.id('field_garage'));
  numberWCInput = element(by.id('field_numberWC'));
  acInput = element(by.id('field_ac'));

  locationSelect = element(by.id('field_location'));
  appuserSelect = element(by.id('field_appuser'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setPriceInput(price: string): Promise<void> {
    await this.priceInput.sendKeys(price);
  }

  async getPriceInput(): Promise<string> {
    return await this.priceInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setBuildingTypeSelect(buildingType: string): Promise<void> {
    await this.buildingTypeSelect.sendKeys(buildingType);
  }

  async getBuildingTypeSelect(): Promise<string> {
    return await this.buildingTypeSelect.element(by.css('option:checked')).getText();
  }

  async buildingTypeSelectLastOption(): Promise<void> {
    await this.buildingTypeSelect.all(by.tagName('option')).last().click();
  }

  async setServiceTypeSelect(serviceType: string): Promise<void> {
    await this.serviceTypeSelect.sendKeys(serviceType);
  }

  async getServiceTypeSelect(): Promise<string> {
    return await this.serviceTypeSelect.element(by.css('option:checked')).getText();
  }

  async serviceTypeSelectLastOption(): Promise<void> {
    await this.serviceTypeSelect.all(by.tagName('option')).last().click();
  }

  async setRefInput(ref: string): Promise<void> {
    await this.refInput.sendKeys(ref);
  }

  async getRefInput(): Promise<string> {
    return await this.refInput.getAttribute('value');
  }

  getVisibleInput(): ElementFinder {
    return this.visibleInput;
  }

  getSoldInput(): ElementFinder {
    return this.soldInput;
  }

  getTerraceInput(): ElementFinder {
    return this.terraceInput;
  }

  async setM2Input(m2: string): Promise<void> {
    await this.m2Input.sendKeys(m2);
  }

  async getM2Input(): Promise<string> {
    return await this.m2Input.getAttribute('value');
  }

  async setNumberBedroomInput(numberBedroom: string): Promise<void> {
    await this.numberBedroomInput.sendKeys(numberBedroom);
  }

  async getNumberBedroomInput(): Promise<string> {
    return await this.numberBedroomInput.getAttribute('value');
  }

  getElevatorInput(): ElementFinder {
    return this.elevatorInput;
  }

  getFurnishedInput(): ElementFinder {
    return this.furnishedInput;
  }

  getPoolInput(): ElementFinder {
    return this.poolInput;
  }

  getGarageInput(): ElementFinder {
    return this.garageInput;
  }

  async setNumberWCInput(numberWC: string): Promise<void> {
    await this.numberWCInput.sendKeys(numberWC);
  }

  async getNumberWCInput(): Promise<string> {
    return await this.numberWCInput.getAttribute('value');
  }

  getAcInput(): ElementFinder {
    return this.acInput;
  }

  async locationSelectLastOption(): Promise<void> {
    await this.locationSelect.all(by.tagName('option')).last().click();
  }

  async locationSelectOption(option: string): Promise<void> {
    await this.locationSelect.sendKeys(option);
  }

  getLocationSelect(): ElementFinder {
    return this.locationSelect;
  }

  async getLocationSelectedOption(): Promise<string> {
    return await this.locationSelect.element(by.css('option:checked')).getText();
  }

  async appuserSelectLastOption(): Promise<void> {
    await this.appuserSelect.all(by.tagName('option')).last().click();
  }

  async appuserSelectOption(option: string): Promise<void> {
    await this.appuserSelect.sendKeys(option);
  }

  getAppuserSelect(): ElementFinder {
    return this.appuserSelect;
  }

  async getAppuserSelectedOption(): Promise<string> {
    return await this.appuserSelect.element(by.css('option:checked')).getText();
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

export class PropertyDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-property-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-property'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
